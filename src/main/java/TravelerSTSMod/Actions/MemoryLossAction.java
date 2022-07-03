package TravelerSTSMod.Actions;

import TravelerSTSMod.Cards.Abstract.IReboundCard;
import TravelerSTSMod.Cards.MemoryLoss;
import TravelerSTSMod.Patches.ReturnToHandOncePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import javax.imageio.event.IIOReadProgressListener;

public class MemoryLossAction extends AbstractGameAction {
    private static final float DURATION = Settings.ACTION_DUR_XFAST;
    private boolean upgraded;

    public MemoryLossAction(AbstractCreature target, boolean upgraded) {
        this.duration = DURATION;
        this.actionType = AbstractGameAction.ActionType.WAIT;
        this.source = AbstractDungeon.player;
        this.target = target;
        this.upgraded = upgraded;
    }

    public void update() {
        if (this.duration == DURATION) {
            AbstractPlayer p = AbstractDungeon.player;

            if (p.discardPile.isEmpty()) {
                this.isDone = true;
                return;
            }
            AbstractCard card = null;
            for (int i = p.discardPile.size() - 1; i >= 0; --i) {
                if (!p.discardPile.group.get(i).cardID.equals(MemoryLoss.ID)) {
                    card = p.discardPile.group.get(i);
                    break;
                }
            }
            if (card == null) {
                isDone = true;
                return;
            }
            p.discardPile.group.remove(card);
            (AbstractDungeon.getCurrRoom()).souls.remove(card);
            p.limbo.group.add(card);
            card.current_y = -200.0F * Settings.scale;
            card.target_x = Settings.WIDTH / 2.0F - 300.0F * Settings.xScale;
            card.target_y = Settings.HEIGHT / 2.0F;
            card.targetAngle = 0.0F;
            card.lighten(false);
            card.drawScale = 0.12F;
            card.targetDrawScale = 0.75F;
            card.applyPowers();
            addToTop(new NewQueueCardAction(card, this.target, false, true));
            addToTop(new UnlimboAction(card));
            if (!Settings.FAST_MODE) {
                addToTop(new WaitAction(Settings.ACTION_DUR_MED));
            } else {
                addToTop(new WaitAction(Settings.ACTION_DUR_FASTER));
            }

            if (upgraded) {
                ReturnToHandOncePatch.returnToHandOnceField.put(card, true);
                card.returnToHand = true;
            }
            this.isDone = true;
        }
    }
}
