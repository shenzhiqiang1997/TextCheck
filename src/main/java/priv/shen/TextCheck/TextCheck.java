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

	static {
		try {
			String propFiles = "file_properties.xml";//加载JWNL参数文件
			JWNL.initialize(TextCheck.class.getClassLoader().getResourceAsStream(propFiles));//JWNL初始化
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String check(String text) {
		//将每个单词取出来
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

		}
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
					int lowestIndex=0;

					//以下是优化部分
					/*//如果是第一个单词且替换建议是将第一个字母变成大写则直接替换
					if (fromPos==0&&replacements.get(0).equalsIgnoreCase(sb.substring(fromPos,toPos))){
						sb=sb.replace(fromPos,toPos,replacements.get(0));
						break;
					}

					//计算错误单词的建议与其他单词的相关度
					int totalScore=0;
					int count=0;
					double lowestScore=20;
					for (int j=0;j<replacements.size();j++) {
						String replacement=replacements.get(j);
						for (int k=0;k<words.length;k++){
							//跳过错误单词
							if (words[k].equals(sb.substring(fromPos,toPos))
									&&positions[k].begin==fromPos
									&&(positions[k].end+1)==toPos)
								continue;

							//计算当前单词与建议单词的相关度
							int score=WNA(IWinit(replacement),IWinit(words[k]));
							//相关度为0的单词为词性不同的单词直接抛弃
							if (score!=0){
								totalScore+=score;
								count++;
							}

						}


						//计算平均相关度
						double averageScore=(double) totalScore/(double) count;
						if (averageScore<lowestScore){
							lowestScore=averageScore;
							lowestIndex=j;
						}

						System.out.println(replacement+","+averageScore);
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
