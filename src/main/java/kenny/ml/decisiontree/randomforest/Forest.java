package kenny.ml.decisiontree.randomforest;

import kenny.ml.decisiontree.Feature;
import kenny.ml.decisiontree.Tree;
import org.apache.commons.lang3.mutable.MutableInt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kenny
 */
public class Forest {

    public List<Tree> trees = new ArrayList<>();

    public String walk(final Feature feature) {
        final Map<String, MutableInt> votes = tallyVotes(feature);
        return highestVote(votes);
    }

    private String highestVote(final Map<String, MutableInt> votes) {
        int highestVoteCount = -1;
        String highestVote = null;
        for(Map.Entry<String, MutableInt> voteEntry : votes.entrySet()) {
            if(voteEntry.getValue().intValue() > highestVoteCount) {
                highestVoteCount = voteEntry.getValue().intValue();
                highestVote = voteEntry.getKey();
            }
        }
        return highestVote;
    }

    private Map<String, MutableInt> tallyVotes(final Feature feature) {
        final Map<String, MutableInt> votes = new HashMap<>();
        for(Tree tree : trees) {
            final String vote = tree.walk(feature);
            if(!votes.containsKey(vote)) {
                votes.put(vote, new MutableInt());
            }
            votes.get(vote).increment();
        }
        return votes;
    }

}
