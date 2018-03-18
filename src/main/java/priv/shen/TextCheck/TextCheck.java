package priv.shen.TextCheck;


import java.io.IOException;
import java.util.List;

import org.languagetool.JLanguageTool;
import org.languagetool.Language;
import org.languagetool.language.BritishEnglish;
import org.languagetool.rules.RuleMatch;

public class TextCheck {
	//得到要纠错分析的文本所属语言
	private static Language language=new BritishEnglish();
	//得到语言工具对象
	private static JLanguageTool jlt=new JLanguageTool(language);

	public static String check(String text) {
		StringBuilder sb=new StringBuilder(text);
		try {
			//根据文本得到在相应规则下的匹配
			List<RuleMatch> matches=jlt.check(text);

			for (int i = matches.size()-1; i >=0 ; i--) {
				RuleMatch match=matches.get(i);
				//得到所有建议的替换文本
				List<String> replacements=match.getSuggestedReplacements();

				//如果有建议的替换文本则替换 否则不替换
				if(replacements!=null&&replacements.size()!=0)
					sb=sb.replace(match.getFromPos(), match.getToPos(), replacements.get(0));
			}

		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		//返回纠过错的文本
		return sb.toString();
	}
}
