package TravelerSTSMod.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

public class WisdomAction extends AbstractGameAction {
    private static final UIStrings uiStrings =
            CardCrawlGame.languagePack.getUIString("TravelerSTSMod:WisdomAction");

    public static final String[] TEXT = uiStrings.TEXT;

    private AbstractPlayer p;

    private static final float DURATION = Settings.ACTION_DUR_XFAST;

    public WisdomAction(AbstractCreature target, AbstractCreature source, int amount) {
        setValues(target, source, amount);
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
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
                this.p.hand.getBottomCard().setCostForTurn(0);
                this.isDone = true;
                return;
            }
            AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false, false);
            tickDuration();
            return;
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            AbstractCard tmpCard = AbstractDungeon.handCardSelectScreen.selectedCards.getBottomCard();
            tmpCard.setCostForTurn(0);
            AbstractDungeon.player.hand.addToHand(tmpCard);
            AbstractDungeon.handCardSelectScreen.selectedCards.clear();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }
        tickDuration();
    }
}
