package kenny.ml.nlp.fleschkincaid;


import kenny.ml.nlp.fleschkincaid.lexer.sentence.StandfordNLPSentenceLexer;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by kenny on 3/11/14.
 */
public class TestFleschKincaid {

    @Test
    public void control() throws IOException {
        //                                            Expected / Actual
        test("control/cat_in_the_hat.txt");       // 111 / 108
        test("control/cnn_article.txt");          // 52  / 44.9
        test("control/gettysburg_address.txt");   // 66  / 63
        test("control/harry_potter.txt");         // 64  / 59.3
        test("control/state_of_the_union.txt");   // 70  / 57.2
        test("control/voter_preference.txt");     // 28.7/ 26.9

        //                                            Expected / Actual
        testStandford("control/cat_in_the_hat.txt");       // 111 / 118
        testStandford("control/cnn_article.txt");          // 52  / 43
        testStandford("control/gettysburg_address.txt");   // 66  / 63
        testStandford("control/harry_potter.txt");         // 64  / 65
        testStandford("control/state_of_the_union.txt");   // 70  / 59
        testStandford("control/voter_preference.txt");     // 28.7/ 26.9
    }

    @Test
    public void misc() throws IOException {
        test("childrens_story1.txt");
        test("childrens_story2.txt");
        test("childrens_story3.txt");
        test("lawyer1.txt");
        test("lawyer2.txt");
        test("lawyer3.txt");

        test("sample_textbook.txt");
        test("sample_essay1.txt");
        test("sample_essay2.txt");
        test("sample_essay3.txt");
        test("sample_essay4.txt");

        test("4th_grade_essay1.txt");

        test("kindergarten_essay1.txt");
        test("kindergarten_essay2.txt");
        test("kindergarten_essay3.txt");
    }

    @Test
    public void kenny() throws IOException {
        test("kennys_essay.txt");
        test("kennys_essay2.txt");
    }

    private void test(String fileName) throws IOException {
        FleschKincaid fleshKincaid = new FleschKincaid();

        System.out.println(fleshKincaid.calculate(
                IOUtils.toString(Thread.currentThread().getContextClassLoader().getResourceAsStream(
                        "kenny/ml/nlp/fleschkincaid/" + fileName))));
    }

    private void testStandford(String fileName) throws IOException {
        FleschKincaid fleshKincaid = new FleschKincaid();
        fleshKincaid.setSentenceLexer(new StandfordNLPSentenceLexer());

        System.out.println(fleshKincaid.calculate(
                IOUtils.toString(Thread.currentThread().getContextClassLoader().getResourceAsStream(
                        "kenny/ml/nlp/fleschkincaid/" + fileName))));
    }

}

