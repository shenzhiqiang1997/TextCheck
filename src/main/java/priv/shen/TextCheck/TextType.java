package priv.shen.TextCheck;

public enum TextType {
    NO_ERROR("无错误",1),
    ONE_WORD_ONE_LETTER_ERROR("其中一个单词 错一个字母",2),
    ONE_WORD_ONE_LETTER_DEFICIENCY("其中一个单词 少一个字母",3),
    TWO_WORD_TWO_LETTER_ERROR("其中两个单词 每个单词错一个字母",4);
    public final String description;
    public final int identifier;

    TextType(String description,int identifier) {
        this.description=description;
        this.identifier=identifier;
    }
}
