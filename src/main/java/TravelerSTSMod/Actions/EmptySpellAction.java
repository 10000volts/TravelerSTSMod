package TravelerSTSMod.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

// 申比写法，好孩子不要学
public class EmptySpellAction extends AbstractGameAction {
    private ArrayList<AbstractCard> handTemp = new ArrayList<>();
    private ArrayList<AbstractCard> canExhaust;

    public EmptySpellAction(ArrayList<AbstractCard> cards) {
        this.canExhaust = cards;
        this.actionType = ActionType.EXHAUST;

        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        if (this.duration == Settings.ACTION_DUR_FAST) {
            // 不存在可消耗的牌
            if (canExhaust.isEmpty())  {
                isDone = true;
                return;
            }

            ArrayList<AbstractCard> cannotExhaust = new ArrayList<>();
            for (AbstractCard cc : p.hand.group) {
                handTemp.add(cc);
                if (!canExhaust.contains(cc)) {
                    cannotExhaust.add(cc);
                }
            }
            p.hand.group.removeAll(cannotExhaust);

            AbstractDungeon.handCardSelectScreen.open(
                    CardCrawlGame.languagePack.getUIString("TravelerSTSMod:EmptySpellAction").TEXT[0], 1,
                    true, true);
            tickDuration();
            return;
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                p.hand.moveToExhaustPile(c);
                handTemp.remove(c);
            }
            CardCrawlGame.dungeon.checkForPactAchievement();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;

            p.hand.group.clear();
            // 加回来
            for (AbstractCard cc : handTemp)
                p.hand.addToTop(cc);
            p.hand.refreshHandLayout();
            this.isDone = true;
        }
        tickDuration();
    }
}
