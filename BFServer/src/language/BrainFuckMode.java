package language;

import java.util.Stack;

/**
 * Created by liao on 2017/6/1.
 */
public class BrainFuckMode implements LanguageMode {


    @Override
    public String execute(String code, String param) {

        char[] codeChars = code.toCharArray();
        int[] memory = new int[10000];
        int pointer = 0;
        StringBuilder result = new StringBuilder();
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < codeChars.length; i++) {

            switch (codeChars[i]) {
                case '>':
                    pointer++;
                    break;
                case '<':
                    pointer--;
                    break;
                case '+':
                    memory[pointer]++;
                    break;
                case '-':
                    memory[pointer]--;
                    break;
                case ',':
                    memory[pointer] = param.charAt(0);
                    param = param.substring(1);
                    break;
                case '.':
                    result.append((char) memory[pointer]);
                    break;
                case '[':
                    if (memory[pointer] == 0) {
                        int bracketCount = 1;
                        while (bracketCount != 0) {
                            if (codeChars[i] == '[') {
                                bracketCount++;
                            } else if (codeChars[i] == ']') {
                                bracketCount--;
                            }
                            i++;
                        }
                    } else {
                        stack.push(i);
                    }
                    break;
                case ']':
                    if (memory[pointer] != 0 && !stack.isEmpty()) {
                        i = stack.pop() - 1;
                    } else {
                        stack.pop();
                    }
                    break;
                default:
                    result.append("error");
            }
        }

        return result.toString();
    }
}
