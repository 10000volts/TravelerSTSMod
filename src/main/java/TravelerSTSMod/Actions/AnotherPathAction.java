package TravelerSTSMod.Actions;

import TravelerSTSMod.Cards.Abstract.ISpecificSentence;
import TravelerSTSMod.Powers.SentencePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

public class AnotherPathAction extends AbstractGameAction {
    private static final UIStrings uiStrings =
            CardCrawlGame.languagePack.getUIString("TravelerSTSMod:AnotherPathAction");
    public static final String[] TEXT = uiStrings.TEXT;
    private AbstractPlayer p;
    private static final float DURATION = Settings.ACTION_DUR_XFAST;
    private boolean upgraded;

    public AnotherPathAction(AbstractCreature target, boolean upgraded) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = DURATION;
        this.p = (AbstractPlayer)target;
        this.upgraded = upgraded;
    }

    public void update() {
        if (this.duration == DURATION) {
            if (this.p.hand.isEmpty()) {
                this.isDone = true;
                return;
            }
            if (this.p.hand.size() == 1) {
                if (p.hand.getBottomCard() instanceof ISpecificSentence) {
                    int n = ((ISpecificSentence) p.hand.getBottomCard()).getSpecificSentence(this.upgraded);
                    if (n != -1) SentencePower.increaseSentence(p, n-SentencePower.getSentence(p));
                }
                this.isDone = true;
                return;
            }
            AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false, false);
            tickDuration();
            return;
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            AbstractCard tmpCard = AbstractDungeon.handCardSelectScreen.selectedCards.getBottomCard();
            if (tmpCard instanceof ISpecificSentence) {
                int n = ((ISpecificSentence) tmpCard).getSpecificSentence(true);
                if (n != -1) SentencePower.increaseSentence(p, n-SentencePower.getSentence(p));
                if (upgraded) {
                    tmpCard.setCostForTurn(tmpCard.costForTurn - 1);
                }
            }
            AbstractDungeon.player.hand.addToHand(tmpCard);
            AbstractDungeon.handCardSelectScreen.selectedCards.clear();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }
        tickDuration();
    }
}
