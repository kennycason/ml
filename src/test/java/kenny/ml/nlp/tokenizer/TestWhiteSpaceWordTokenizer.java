package kenny.ml.nlp.tokenizer;

import ch.lambdaj.Lambda;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestWhiteSpaceWordTokenizer {

    private static final Logger LOGGER = Logger.getLogger(TestWhiteSpaceWordTokenizer.class);

    @Test
    public void english() {
        final WordTokenizer tokenizer = new WhiteSpaceWordTokenizer();
        List<String> words = tokenizer.tokenize(
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua." +
                " Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.");
        LOGGER.info(Lambda.join(words));
        assertEquals(36, words.size());
    }

    @Test
    public void russian() {
        final WordTokenizer tokenizer = new WhiteSpaceWordTokenizer();
        List<String> words = tokenizer.tokenize(
                "Кюм агам аугюэ компрэхэнжам ут, кибо тимэам промпта йн ючю. Нам нэ дыкоры каючаэ. Нык эю едквюэ каючаэ. Эа эжт зюаз диам этёам. " +
                "Ед эож утамюр эффикеэнди. Жят граэки кытэрож экз, нужквюам пэрчыквюэрёж ыт прё, ёнанй дежпютатионй хёз ан.");
        LOGGER.info(Lambda.join(words));
        assertEquals(39, words.size());
    }

    @Test
    public void korean() {
        final WordTokenizer tokenizer = new WhiteSpaceWordTokenizer();
        List<String> words = tokenizer.tokenize(
                "사용할 수있는 구절 많은 변화가 있지만, 대부분의, 주입 유머로, 어떤 형태의 변경을 입었거나 조금이라도 믿을 보이지 않는 단어를 무작위. " +
                "당신은 Lorem Ipsum의 통로를 사용하려는 경우, 당신은 텍스트의 가운데에 숨겨진 뭔가 당황 없다는 확신해야합니다.");
        LOGGER.info(Lambda.join(words));
        assertEquals(33, words.size());
    }

    @Test
    public void arabic() {
        final WordTokenizer tokenizer = new WhiteSpaceWordTokenizer();
        List<String> words = tokenizer.tokenize(
                "نفس المحيط وهولندا، لم, بين دارت مئات أي, عام ما باستخدام تشيكوسلوفاكيا. ثم قبل خيار بغزو أطراف. بغزو والنرويج لمّ هو. بها تنفّس الشتاء، ان. مليارات ومحاولة في حدة, روسية تغييرات ماليزيا، أن حين. بقيادة بينيتو بعد ثم, ليركز ماحت و. بحث عُقر المحيط إذ, بهيئة الصينية مدن ثم.");
        LOGGER.info(Lambda.join(words));
        assertEquals(49, words.size());
    }

}
