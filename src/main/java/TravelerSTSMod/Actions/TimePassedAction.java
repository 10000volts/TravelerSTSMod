package TravelerSTSMod.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

public class TimePassedAction extends AbstractGameAction {
    private static final UIStrings uiStrings =
            CardCrawlGame.languagePack.getUIString("CopyAction");

    public static final String[] TEXT = uiStrings.TEXT;

    private AbstractPlayer p;

    private static final float DURATION = Settings.ACTION_DUR_XFAST;

    public TimePassedAction(AbstractCreature target, AbstractCreature source, int amount) {
        setValues(target, source, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = DURATION;
        this.p = (AbstractPlayer)target;
    }

    public void update() {
        if (this.duration == DURATION) {
            if (this.p.hand.isEmpty()) {
                this.isDone = true;
                return;
            }
            if (this.p.hand.size() == 1) {
                AbstractCard tmpCard = p.hand.group.get(0);
                addToTop(new MakeTempCardInHandAction(tmpCard.makeStatEquivalentCopy()));
                this.isDone = true;
                return;
            }
            AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false, false);
            tickDuration();
            return;
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            AbstractCard tmpCard = AbstractDungeon.handCardSelectScreen.selectedCards.getBottomCard();
            addToTop(new MakeTempCardInHandAction(tmpCard.makeStatEquivalentCopy()));
            addToTop(new MakeTempCardInHandAction(tmpCard.makeStatEquivalentCopy()));
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.clear();
        }
        tickDuration();
    }
}
