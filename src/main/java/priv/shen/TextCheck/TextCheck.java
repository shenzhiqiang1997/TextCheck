package priv.shen.TextCheck;


import java.io.IOException;
import java.util.List;

import net.didion.jwnl.JWNL;
import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.IndexWordSet;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.data.PointerType;
import net.didion.jwnl.data.relationship.Relationship;
import net.didion.jwnl.data.relationship.RelationshipFinder;
import net.didion.jwnl.data.relationship.RelationshipList;
import net.didion.jwnl.dictionary.Dictionary;
import org.languagetool.JLanguageTool;
import org.languagetool.Language;
import org.languagetool.language.BritishEnglish;
import org.languagetool.rules.RuleMatch;

public class TextCheck {
	//得到要纠错分析的文本所属语言
	private static Language language=new BritishEnglish();
	//得到语言工具对象
	private static JLanguageTool jlt=new JLanguageTool(language);

	/*static {
		try {
			String propFiles = "file_properties.xml";//加载JWNL参数文件
			JWNL.initialize(TextCheck.class.getClassLoader().getResourceAsStream(propFiles));//JWNL初始化
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	public static String check(String text) {
		/*//将每个单词取出来
		String[] words=text.split(" ");
		if (words!=null) {
			if (!(words[words.length - 1].endsWith("") || words[words.length - 1].endsWith(" "))) {
				words[words.length - 1] = words[words.length - 1].replace(".", "");
				words[words.length - 1] = words[words.length - 1].replace("?", "");
				words[words.length - 1] = words[words.length - 1].replace("!", "");
			}
		}

		//记录每个单词的首尾位置
		Position[] positions=new Position[words.length];
		int index=0;
		for (int i=0;i<words.length;i++) {
			String word=words[i];
			int begin=index,end=index+word.length()-1;
			//将单词的开始和结尾记录下来
			positions[i]=new Position(begin,end);
			index=end+2;

		}*/
		StringBuilder sb=new StringBuilder(text);
		try {
			//根据文本得到在相应规则下的匹配
			List<RuleMatch> matches=jlt.check(text);

			for (int i = matches.size()-1; i >=0 ; i--) {
				RuleMatch match=matches.get(i);
				//得到所有建议的替换文本
				List<String> replacements=match.getSuggestedReplacements();

				//如果有建议的替换文本则替换 否则不替换
				if(replacements!=null&&replacements.size()!=0) {
					//得到错误单词的起始位置
					int fromPos = match.getFromPos();
					int toPos = match.getToPos();
/*
					String wordBefore=null;
					String wordAfter=null;
					//找到前面的单词和后面的单词
					for (int j = 0; j <words.length ; j++) {
						if (words[j].equals(sb.substring(fromPos,toPos))&&positions[j].begin==fromPos&&(positions[j].end+1)==toPos){
							if ((j-1>=0))
								wordBefore=words[j-1];
							if (j+1<words.length)
								wordAfter=words[j+1];
						}
					}
					//计算错误单词的建议与前后单词的相关度
					int score1=10,score2=10;*/
					int lowestScore=10,lowestIndex=0;
					/*for (int j=0;j<replacements.size();j++) {
						String replacement=replacements.get(j);
						if (wordBefore!=null)
							score1=WNA(IWinit(wordBefore),IWinit(replacement));
						if (wordAfter!=null)
							score2=WNA(IWinit(wordAfter),IWinit(replacement));
						int score=(score1+score2)/2;
						*//*System.out.println(score1+","+score2+","+replacement);*//*
						if (score<10&&score<lowestScore){
							lowestScore=score;
							lowestIndex=j;
						}
					}*/

					sb = sb.replace(fromPos, toPos, replacements.get(lowestIndex));
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		//返回纠过错的文本
		return sb.toString();
	}
	public static IndexWord IWinit(String word){//单词初始化，IndexWordinit
		try{
			return Dictionary.getInstance().lookupIndexWord(POS.NOUN,word);
		}
		catch(Exception e){
			return null;
		}
	}

	public static int WNA(IndexWord kw1, IndexWord kw2){//词语网络分析，WordNetAnalysis
		try {
			RelationshipList list = RelationshipFinder.getInstance().findRelationships(kw1.getSense(1), kw2.getSense(1), PointerType.HYPERNYM);
			return ((Relationship) list.get(0)).getDepth();
		}
		catch(Exception e){
			return 0;
		}
	}
}
