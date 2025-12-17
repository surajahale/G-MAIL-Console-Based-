import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

class User {
    private String name;
    private long contact;
    private String mail;
    private String dob;
    private String password;

    private ArrayList<Mail> sendMail = new ArrayList<Mail>();
    private ArrayList<Mail> inboxMail = new ArrayList<Mail>();
    private ArrayList<Mail> starredMail = new ArrayList<Mail>();
    private ArrayList<Mail> draftMail = new ArrayList<Mail>();
    private ArrayList<Mail> binMail = new ArrayList<Mail>();

    public User(String name, long contact, String mail, String dob, 
        String password) {
        super();
        this.name = name;
        this.contact = contact;
        this.mail = mail;
        this.dob = dob;
        this.password = password;
    }

    public String getMail() {
        return this.mail;
    }

    public String getPassword() {
        return this.password;
    }

    public String getName() {
        return this.name;
    }

    public void addSendMail(Mail send) {
        sendMail.add(send);
    }

    public void addInboxMail(Mail inbox) {
        inboxMail.add(inbox);
    }

    public void addDraftMail(Mail draft) {
        draftMail.add(draft);
    }

    public ArrayList<Mail> getInboxMail() {
        return inboxMail;
    }

    public ArrayList<Mail> getSendMail() {
        return sendMail;
    }

    public ArrayList<Mail> getDraftMail() {
        return draftMail;
    }
}

class Mail {
    private String sender;
    private String receiver;
    private String subject;
    private String body;

    public Mail(String sender, String receiver, String subject, String body) {
        super();
        this.sender = sender;
        this.receiver = receiver;
        this.subject = subject;
        this.body = body;
    }

    public void getMailInfo() {
        System.out.println("*** MAIL ***");
        System.out.println("Sender : " + sender);
        System.out.println("Receiver : " + receiver);
        System.out.println("Subject : " + subject);
        System.out.println("Body : " + body);
    }
}


class Gmail {
    ArrayList<User> userList = new ArrayList<>();

    public void launchApplication() {
        while (true) {
            System.out.println("\n*** WELCOME TO GMAIL ***");
            System.out.println("1. CREATE ACCOUNT");
            System.out.println("2. LOGIN");
            System.out.println("3. EXIT");
            System.out.print("Enter your option: ");

            int option = new Scanner(System.in).nextInt();

            switch (option) {
                case 1 -> createAccount();
                case 2 -> login();
                case 3 -> System.exit(0);
                default -> System.out.println("\nINVALID OPTION\n");
            }
        }
    }

    private void login() {
        System.out.println("\nLOGIN MODULE");
        System.out.print("Email: ");
        String userMail = new Scanner(System.in).next();

        if (!userMail.endsWith("@gmail.com")) {
            userMail += "@gmail.com";
        }

        System.out.print("Password: ");
        String userPassword = new Scanner(System.in).next();

        for (User user : userList) {
            if (userMail.equals(user.getMail()) && userPassword.equals
                (user.getPassword())) {
                homepage(user);
                return;
            }
        }
        System.out.println("\nINVALID CREDENTIALS\n");
    }

    // CREATE ACCOUNT MODULE
    private void createAccount() {
        System.out.println("\nCREATE ACCOUNT");

        System.out.print("First Name: ");
        String first = new Scanner(System.in).next();

        System.out.print("Last Name: ");
        String last = new Scanner(System.in).next();

        System.out.print("Contact: ");
        long contact = new Scanner(System.in).nextLong();

        String mail = null;

        outer:
        while (true) {
            System.out.print("Email: ");
            mail = new Scanner(System.in).next();

            for (User u : userList) {
                if (mail.equals(u.getMail())) {
                    System.out.println("\nMAIL ALREADY EXISTS\n");
                    String[] sugg = suggestion(first);
                    System.out.println(Arrays.toString(sugg));
                    continue outer;
                }
            }
            break;
        }

        System.out.print("DOB: ");
        String dob = new Scanner(System.in).next();

        System.out.print("Password: ");
        String password = new Scanner(System.in).next();

        User newUser = new User(first + " " + last, contact, mail, dob, password);
        userList.add(newUser);

        System.out.println("\nACCOUNT CREATED SUCCESSFULLY!\n");
    }

    private String[] suggestion(String name) {
        String[] sug = new String[3];

        for (int i = 0; i < 3; i++) {
            String random = "";
            for (int j = 0; j < 4; j++) {
                int d = (int) (Math.random() * 10);
                random += d;
            }
            sug[i] = name + random + "@gmail.com";
        }
        return sug;
    }

    private void homepage(User user) {
        while (true) {
            System.out.println("\n***** HOME PAGE *****");
            System.out.println("1. Compose Mail");
            System.out.println("2. Draft Mail");
            System.out.println("3. Send Mail");
            System.out.println("4. Inbox");
            System.out.println("5. All Mail");
            System.out.println("6. Logout");
            System.out.print("Enter your option: ");

            int opt = new Scanner(System.in).nextInt();

            switch (opt) {
                case 1 -> composeMail(user);
                case 2 -> draftModule(user);
                case 3 -> sendModule(user);
                case 4 -> inboxModule(user);
                case 5 -> allMailModule(user);
                case 6 -> { 
                    System.out.println("Logged out.\n");
                    return;
                }
                default -> System.out.println("Invalid");
            }
        }
    }

    private void allMailModule(User user) {
        System.out.println("\n--- ALL MAIL ---");
        sendModule(user);
        inboxModule(user);
    }

    private void inboxModule(User user) {
        System.out.println("\n--- INBOX ---");
        for (Mail m : user.getInboxMail()) {
            m.getMailInfo();
            System.out.println("----------------------");
        }
    }

    private void sendModule(User user) {
        System.out.println("\n--- SENT MAIL ---");
        for (Mail m : user.getSendMail()) {
            m.getMailInfo();
            System.out.println("----------------------");
        }
    }

    
    private void draftModule(User user) {
        System.out.println("\n--- DRAFT MAIL ---");
        for (Mail m : user.getDraftMail()) {
            m.getMailInfo();
            System.out.println("----------------------");
        }
    }

    private void composeMail(User user) {
        User toUser = null;

        System.out.println("\nCOMPOSE MAIL");
        System.out.println("From: " + user.getMail());

        while (true) {
            System.out.print("To: ");
            String to = new Scanner(System.in).next();

            for (User u : userList) {
                if (to.equals(u.getMail())) {
                    toUser = u;
                    break;
                }
            }

            if (toUser != null) break;

            System.out.println("User Not Found! Try Again.");
        }

        System.out.print("Subject: ");
        String subject = new Scanner(System.in).nextLine();

        System.out.print("Body: ");
        String body = new Scanner(System.in).nextLine();

        Mail newMail = new Mail(user.getMail(), toUser.getMail(), subject, body);

        System.out.print("Send Mail? (yes/no): ");
        String ans = new Scanner(System.in).next();

        if (ans.equalsIgnoreCase("yes")) {
            user.addSendMail(newMail);
            toUser.addInboxMail(newMail);
            System.out.println("MAIL SENT!");
        } else {
            user.addDraftMail(newMail);
            System.out.println("MAIL SAVED TO DRAFTS.");
        }
    }
}

public class GmailDriver {
    public static void main(String[] args) {
        Gmail gmail = new Gmail();
        gmail.launchApplication();
    }
}


