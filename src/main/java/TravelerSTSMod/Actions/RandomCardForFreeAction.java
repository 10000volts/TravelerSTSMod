package TravelerSTSMod.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class RandomCardForFreeAction extends AbstractGameAction {
    private AbstractCard.CardTags tags;
    private ArrayList<AbstractCard> cl;

    public RandomCardForFreeAction(AbstractCard.CardTags t) {
        tags = t;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            cl = new ArrayList<>();
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (tags != null) {
                    if (!c.tags.contains(tags)) continue;
                }
                if (c.costForTurn > 0) {
                    cl.add(c);
                }
            }
            if (!cl.isEmpty()) findAndModifyCard();
        }
        tickDuration();
    }

    private void findAndModifyCard() {
        AbstractCard c = cl.get(AbstractDungeon.cardRandomRng.random(cl.size() - 1));
        if (tags != null && !c.tags.contains(tags)) findAndModifyCard();
        if (c.costForTurn > 0) {
            c.setCostForTurn(0);
            c.isCostModified = true;
            c.superFlash();
        } else {
            findAndModifyCard();
        }
    }
}
