package TravelerSTSMod.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;

public class RecallAction extends AbstractGameAction {
    private static final UIStrings uiStrings =
            CardCrawlGame.languagePack.getUIString("BetterToHandAction");
    public static final String[] TEXT = uiStrings.TEXT;
    private AbstractPlayer p;
    private static final float DURATION = Settings.ACTION_DUR_FAST;

    public RecallAction(AbstractCreature target) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = DURATION;
        this.p = (AbstractPlayer)target;
    }

    public void update() {
        if (p.discardPile.isEmpty()) {
            this.isDone = true;
            return;
        }
        ArrayList<AbstractCard> handTemp = new ArrayList<>(p.hand.group);
        for (AbstractCard c : handTemp) {
            p.hand.moveToDiscardPile(c);
        }
        ArrayList<AbstractCard> d = new ArrayList<>(p.hand.group);
        for (AbstractCard c : p.discardPile.group) {
            if (handTemp.contains(c)) continue;
            d.add(c);
        }
        for (AbstractCard c : d) {
            p.hand.addToHand(c);
            p.discardPile.removeCard(c);
            c.lighten(false);
            c.applyPowers();
        }
        this.isDone = true;
    }
}
