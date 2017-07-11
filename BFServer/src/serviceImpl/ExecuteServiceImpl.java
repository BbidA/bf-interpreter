package serviceImpl;

import language.BrainFuckMode;
import language.LanguageMode;
import language.OokMode;
import service.ExecuteService;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ExecuteServiceImpl implements ExecuteService {

    private static Map<String, Supplier<LanguageMode>> languageModeMap = new HashMap<>();

    static {
        languageModeMap.put(".ook", OokMode::new);
        languageModeMap.put(".bf", BrainFuckMode::new);
    }

    /**
     * 根据后缀自动执行代码
     */
    @Override
    public String execute(String code, String param, String suffix) throws RemoteException {

        LanguageMode languageMode = languageModeMap.get(suffix).get();
        System.out.println("succeed");

        return languageMode.execute(code, param);
    }
}
