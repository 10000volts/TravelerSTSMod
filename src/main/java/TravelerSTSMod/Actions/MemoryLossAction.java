package TravelerSTSMod.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
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
            if (AbstractDungeon.player.discardPile.isEmpty()) {
                this.isDone = true;
                return;
            }
            AbstractCard card = null;
            for (int i = AbstractDungeon.player.discardPile.size() - 1; i >= 0; --i) {
                if (AbstractDungeon.player.discardPile.group.get(i).cardID.equals("TravelerSTSMod:MemoryLoss")) {
                    card = AbstractDungeon.player.discardPile.group.get(i);
                }
            }
            if (card == null) {
                isDone = true;
                return;
            }
            AbstractDungeon.player.limbo.group.add(card);
            if (upgraded) card.returnToHand = true;
            card.current_y = -200.0F * Settings.scale;
            card.target_x = Settings.WIDTH / 2.0F + 200.0F * Settings.xScale;
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
            this.isDone = true;
        }
    }
}
