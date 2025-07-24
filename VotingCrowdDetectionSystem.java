import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class VotingCrowdDetectionSystem {

    // Twilio credentials
    public static final String ACCOUNT_SID = "ACa883034ba9ffxxxxxxxxxxxxxxxxxxxx";
    public static final String AUTH_TOKEN = "7f61925fd179907a5xxxxxxxxxxxxxxx";
    public static final String TWILIO_PHONE_NUMBER = "+1269xxxxxx";

    public static void main(String[] args) {
        // Initialize Twilio
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Scanner scanner = new Scanner(System.in);

        // Input for crowd threshold and current crowd level
        System.out.print("Enter crowd threshold percentage: ");
        int crowdThreshold = scanner.nextInt();

        System.out.print("Enter current crowd level percentage: ");
        int crowdLevel = scanner.nextInt();

        System.out.println("Current crowd level: " + crowdLevel + "%");

        // Predefined list of phone numbers
        List<String> phoneNumbers = Arrays.asList(
                "+91xxxxxxxxxx",
                "+91xxxxxxxxxx",
                "+91xxxxxxxxxx",
                "+91xxxxxxxxxx",
                "+91xxxxxxxxxx"
        );

        // Determine the appropriate message based on the crowd level
        String messageBody;
        if (crowdLevel > crowdThreshold) {
            // Send notification when the place is crowded
            messageBody = "This place is crowded. Please choose a different time.";
        } else {
            // Send notification when the place is not crowded
            messageBody = "This voting place is not crowded. You can proceed.";
        }

        // Send notification to all predefined numbers
        for (String phoneNumber : phoneNumbers) {
            sendNotification(phoneNumber, messageBody);
        }

        // Confirmation message
        System.out.println("Notification sent to all predefined numbers.");
    }

    // Method to send SMS notifications
    public static void sendNotification(String toPhoneNumber, String messageBody) {
        try {
            // Send the message using Twilio API
            Message message = Message.creator(
                    new PhoneNumber(toPhoneNumber), // Destination phone number
                    new PhoneNumber(TWILIO_PHONE_NUMBER), // Twilio phone number
                    messageBody // Message content
            ).create();

            // Print message SID for confirmation
            System.out.println("Notification sent to " + toPhoneNumber + ": " + message.getSid());
        } catch (Exception e) {
            // Handle any error that occurs while sending the message
            System.out.println("Error sending message to " + toPhoneNumber + ": " + e.getMessage());
        }
    }
}