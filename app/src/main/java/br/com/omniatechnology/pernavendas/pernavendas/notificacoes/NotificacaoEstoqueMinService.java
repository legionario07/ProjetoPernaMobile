package br.com.omniatechnology.pernavendas.pernavendas.notificacoes;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.ConfiguracaoServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.api.impl.ProdutoServiceImpl;
import br.com.omniatechnology.pernavendas.pernavendas.model.Configuracao;
import br.com.omniatechnology.pernavendas.pernavendas.model.Produto;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ConstraintUtils;
import br.com.omniatechnology.pernavendas.pernavendas.utils.SessionUtil;
import br.com.omniatechnology.pernavendas.pernavendas.utils.VerificaConexaoStrategy;
import rx.Observer;

public class NotificacaoEstoqueMinService extends Service {

    private int startId;
    public static boolean ativo = false;
    private Context context;
    private List<Produto> produtos = new ArrayList<>();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();


        startId = -1;
        if (ativo) {
            return;
        }
        ativo = true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.startId = startId;

        context = getBaseContext();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                while(ativo){


                    if(VerificaConexaoStrategy.verificarConexao(context)) {

                        if(SessionUtil.getInstance().getQtdeMinEstoqueDefault()==null){


                            new ConfiguracaoServiceImpl().findByPropriedade(ConstraintUtils.NOTIFICACAO_QTDE_MIN_DEFAULT_STR)
                                    .subscribe(new Observer<Configuracao>() {
                                        @Override
                                        public void onCompleted() {
                                            verificarEstoque();

                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                        }

                                        @Override
                                        public void onNext(Configuracao configuracao) {
                                            try {
                                                SessionUtil.getInstance().setQtdeMinEstoqueDefault(Integer.valueOf(configuracao.getValor()));
                                            }catch (Exception e){
                                                SessionUtil.getInstance().setQtdeMinEstoqueDefault(1);
                                            }
                                        }
                                    });

                        }else{
                            SessionUtil.getInstance().setQtdeMinEstoqueDefault(ConstraintUtils.NOTIFICACAO_QTDE_MIN_DEFAULT);
                            verificarEstoque();
                        }



                    }

                    try {
                        Thread.sleep(1_800_000);
                    } catch (InterruptedException e) {
                        Log.e(ConstraintUtils.TAG,e.getMessage());
                        e.printStackTrace();
                    }

                }

            }
        });
        t.start();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        stopSelf(startId);
        ativo = false;

    }

    private void verificarEstoque(){
        new ProdutoServiceImpl().findAll()
                .subscribe(new Observer<List<Produto>>() {
                    @Override
                    public void onCompleted() {

                        if (produtos!=null && !produtos.isEmpty()) {

                            List<Produto> listaTemp = new ArrayList<>();

                            for(Produto p : produtos){
                                if(p.getQtde()<=p.getQtdeMinima() && p.getQtdeMinima()>=SessionUtil.getInstance().getQtdeMinEstoqueDefault()){
                                    listaTemp.add(p);
                                }
                            }

                            for(Produto produto : listaTemp) {
                                Notificacao.criarNotificacao(context, getString(R.string.produtos_acabando), produto);
                            }
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(List<Produto> produtosTemp) {
                        if (produtos != null) {
                            produtos.clear();
                            produtos.addAll(produtosTemp);
                        }
                    }
                });

    }
}