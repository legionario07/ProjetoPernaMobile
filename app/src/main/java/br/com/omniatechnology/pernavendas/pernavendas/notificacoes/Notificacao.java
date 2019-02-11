package br.com.omniatechnology.pernavendas.pernavendas.notificacoes;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.model.Produto;
import br.com.omniatechnology.pernavendas.pernavendas.telas.LoginActivity;
import br.com.omniatechnology.pernavendas.pernavendas.utils.ConstraintUtils;

import static android.content.Context.VIBRATOR_SERVICE;

public class Notificacao {

    public static void criarNotificacao(Context context, String mensagem, Produto produto) {

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, LoginActivity.class), 0);

        StringBuilder mensagemBuilder = new StringBuilder();
        mensagemBuilder.append(mensagem);
        mensagemBuilder.append("\n");
        mensagemBuilder.append("Produto: ");
        mensagemBuilder.append(produto.getNome());
        mensagemBuilder.append("\t");
        mensagemBuilder.append(" - Qtde: ");
        mensagemBuilder.append(produto.getQtde());

        Log.i(ConstraintUtils.TAG, "criarNotificacao: "+mensagemBuilder.toString());

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setTicker(context.getString(R.string.perna_vendas))
                .setContentTitle(context.getString(R.string.perna_vendas))
                .setContentText(mensagemBuilder.toString())
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
           builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setColor(NotificationCompat.COLOR_DEFAULT);
        } else {
            builder.setSmallIcon(R.mipmap.ic_launcher);
        }


        android.app.Notification not = builder.build();
        not.flags = android.app.Notification.FLAG_AUTO_CANCEL;

        Vibrator vibrator = (Vibrator) context.getSystemService(VIBRATOR_SERVICE);
        long milliseconds = 500;
        vibrator.vibrate(milliseconds);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, not);

    }


}