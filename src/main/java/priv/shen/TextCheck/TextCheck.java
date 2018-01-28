package priv.shen.TextCheck;

import java.io.IOException;
import java.util.List;

import org.languagetool.JLanguageTool;
import org.languagetool.Language;
import org.languagetool.language.BritishEnglish;
import org.languagetool.rules.RuleMatch;

public class TextCheck {
	public static String check(String text) {
		//�?要纠错分析的文本�?属语�?
		Language language=new BritishEnglish();
		//得到语言工具对象
		JLanguageTool jlt=new JLanguageTool(language);
		
		StringBuilder sb=new StringBuilder(text);
		try {
			//根据文本得到在相应规则下的匹�?
			List<RuleMatch> ruleMatchs=jlt.check(text);
			System.out.println("postion:suggestion");
			for (RuleMatch match : ruleMatchs) {
				System.out.println(match.getFromPos()+"-"+match.getToPos()+":"+match.getMessage());
				//按照替换建议对文本进行纠�?
				sb.replace(match.getFromPos(), match.getToPos(), match.getSuggestedReplacements().get(0));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		//返回纠过错的文本
		return sb.toString();
	}
}
