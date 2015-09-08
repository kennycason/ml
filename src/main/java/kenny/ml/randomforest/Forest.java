package kenny.ml.randomforest;

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

    public Enum walk(final Feature feature) {
        final Map<Enum, MutableInt> votes = tallyVotes(feature);
        return highestVote(votes);
    }

    private Enum highestVote(final Map<Enum, MutableInt> votes) {
        int highestVoteCount = -1;
        Enum highestVote = null;
        for(Map.Entry<Enum, MutableInt> voteEntry : votes.entrySet()) {
            if(voteEntry.getValue().intValue() > highestVoteCount) {
                highestVoteCount = voteEntry.getValue().intValue();
                highestVote = voteEntry.getKey();
            }
        }
        return highestVote;
    }

    private Map<Enum, MutableInt> tallyVotes(final Feature feature) {
        final Map<Enum, MutableInt> votes = new HashMap<>();
        for(Tree tree : trees) {
            final Enum vote = tree.walk(feature);
            if(!votes.containsKey(vote)) {
                votes.put(vote, new MutableInt());
            }
            votes.get(vote).increment();
        }
        return votes;
    }

}
