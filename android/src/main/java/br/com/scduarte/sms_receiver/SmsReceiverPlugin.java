package br.com.scduarte.sms_receiver;

import androidx.annotation.NonNull;
import br.com.scduarte.sms_receiver.permisions.Permissions;
import br.com.scduarte.sms_receiver.status.SmsStateHandler;

import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.JSONMethodCodec;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry.Registrar;
import io.flutter.plugin.common.StandardMethodCodec;

/** SmsReceiverPlugin */
public class SmsReceiverPlugin {
  private static final String CHANNEL_RECV = "plugins.scduarte.com/recvSMS";
    private static final String CHANNEL_SMS_STATUS = "plugins.scduarte.com/statusSMS";
    private static final String CHANNEL_SEND = "plugins.scduarte.com/sendSMS";
    private static final String CHANNEL_QUER = "plugins.scduarte.com/querySMS";
    private static final String CHANNEL_QUER_CONT = "plugins.scduarte.com/queryContact";
    private static final String CHANNEL_QUER_CONT_PHOTO = "plugins.scduarte.com/queryContactPhoto";
    private static final String CHANNEL_USER_PROFILE = "plugins.scduarte.com/userProfile";
    private static final String CHANNEL_SIM_CARDS = "plugins.scduarte.com/simCards";
    private static final String CHANNEL_REMOVE = "scduarte.sms.remove.channel";
    /**
     * Plugin registration.
     */
    public static void registerWith(Registrar registrar) {

        registrar.addRequestPermissionsResultListener(Permissions.getRequestsResultsListener());

        // SMS remover
        final SmsRemover smsRemover = new SmsRemover(registrar);
        final MethodChannel removeSmsChannel = new MethodChannel(registrar.messenger(), CHANNEL_REMOVE);
        removeSmsChannel.setMethodCallHandler(smsRemover);

        // SMS receiver
        final SmsReceiver receiver = new SmsReceiver(registrar);
        final EventChannel receiveSmsChannel = new EventChannel(registrar.messenger(),
                CHANNEL_RECV, JSONMethodCodec.INSTANCE);
        receiveSmsChannel.setStreamHandler(receiver);

        // SMS status receiver
        new EventChannel(registrar.messenger(), CHANNEL_SMS_STATUS, JSONMethodCodec.INSTANCE)
                .setStreamHandler(new SmsStateHandler(registrar));

        /// SMS sender
        final SmsSender sender = new SmsSender(registrar);
        final MethodChannel sendSmsChannel = new MethodChannel(registrar.messenger(),
                CHANNEL_SEND, JSONMethodCodec.INSTANCE);
        sendSmsChannel.setMethodCallHandler(sender);

        /// SMS query
        final SmsQuery query = new SmsQuery(registrar);
        final MethodChannel querySmsChannel = new MethodChannel(registrar.messenger(), CHANNEL_QUER, JSONMethodCodec.INSTANCE);
        querySmsChannel.setMethodCallHandler(query);

        /// Contact query
        final ContactQuery contactQuery = new ContactQuery(registrar);
        final MethodChannel queryContactChannel = new MethodChannel(registrar.messenger(), CHANNEL_QUER_CONT, JSONMethodCodec.INSTANCE);
        queryContactChannel.setMethodCallHandler(contactQuery);

        /// Contact Photo query
        final ContactPhotoQuery contactPhotoQuery = new ContactPhotoQuery(registrar);
        final MethodChannel queryContactPhotoChannel = new MethodChannel(registrar.messenger(), CHANNEL_QUER_CONT_PHOTO, StandardMethodCodec.INSTANCE);
        queryContactPhotoChannel.setMethodCallHandler(contactPhotoQuery);

        /// User Profile
        final UserProfileProvider userProfileProvider = new UserProfileProvider(registrar);
        final MethodChannel userProfileProviderChannel = new MethodChannel(registrar.messenger(), CHANNEL_USER_PROFILE, JSONMethodCodec.INSTANCE);
        userProfileProviderChannel.setMethodCallHandler(userProfileProvider);

        //Sim Cards Provider
        new MethodChannel(registrar.messenger(), CHANNEL_SIM_CARDS, JSONMethodCodec.INSTANCE)
                .setMethodCallHandler(new SimCardsProvider(registrar));
    }
}
