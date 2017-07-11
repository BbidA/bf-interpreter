package language;

/**
 * Created by liao on 2017/6/1.
 */
public class OokMode implements LanguageMode {

    @Override
    public String execute(String code, String param) {

        String[] codeString = code.split("Ook");

        for (int i = 0; i < codeString.length; i++) {

            codeString[i] = codeString[i].trim();

        }

        StringBuilder translate = new StringBuilder();
        BrainFuckMode brainFuckMode = new BrainFuckMode();


        for (int i = 1; i < codeString.length; i += 2) {
            switch (codeString[i]) {
                case ".":
                    if (codeString[i + 1].equals("?")) {
                        translate.append(">");
                    } else if (codeString[i + 1].equals(".")) {
                        translate.append("+");
                    } else if (codeString[i + 1].equals("!")) {
                        translate.append(",");
                    }
                    break;
                case "?":
                    if (codeString[i + 1].equals(".")) {
                        translate.append("<");
                    } else if (codeString[i + 1].equals("!")) {
                        translate.append("]");
                    }
                    break;
                case "!":
                    if (codeString[i + 1].equals("!")) {
                        translate.append("-");
                    } else if (codeString[i + 1].equals(".")) {
                        translate.append(".");
                    } else if (codeString[i + 1].equals("?")) {
                        translate.append("[");
                    }
            }
        }
        return brainFuckMode.execute(translate.toString(), param);
    }

    public static void main(String[] args) {
        OokMode ookMode = new OokMode();
        System.out.println(ookMode.execute("Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook! Ook? Ook. Ook? Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook! Ook? Ook. Ook? Ook. Ook. Ook. Ook? Ook. Ook. Ook. Ook? Ook. Ook. Ook. Ook? Ook. Ook. Ook. Ook? Ook. Ook. Ook. Ook? Ook. Ook. Ook? Ook. Ook? Ook. Ook? Ook. Ook? Ook. Ook? Ook. Ook? Ook. Ook! Ook! Ook? Ook! Ook? Ook. Ook! Ook! Ook? Ook! Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook! Ook? Ook. Ook? Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook! Ook? Ook. Ook? Ook. Ook? Ook. Ook. Ook. Ook? Ook! Ook! Ook? Ook. Ook? Ook. Ook? Ook. Ook! Ook! Ook? Ook! Ook? Ook. Ook! Ook! Ook? Ook! Ook. Ook? Ook. Ook? Ook. Ook? Ook! Ook! Ook! Ook! Ook! Ook! Ook. Ook? Ook! Ook! Ook! Ook! Ook! Ook! Ook! Ook! Ook. Ook? Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook? Ook. Ook. Ook. Ook. Ook. Ook? Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook? Ook. Ook? Ook. Ook? Ook. Ook? Ook. Ook? Ook. Ook! Ook. Ook. Ook? Ook! Ook. Ook. Ook? Ook! Ook. Ook. Ook? Ook! Ook. Ook. Ook? Ook! Ook. Ook. Ook? Ook! Ook. ", ""));
    }
}
