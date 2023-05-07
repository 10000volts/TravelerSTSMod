package TravelerSTSMod.Actions;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.SoulGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DrawUntilNotAttackCardAction extends DrawCardAction {
    private static final Logger logger = LogManager.getLogger(DrawCardAction.class.getName());
    public static AbstractCard drawnCard;


    public DrawUntilNotAttackCardAction() {
        super(1);
    }

    @Override
    public void update() {
        if (AbstractDungeon.player.hasPower("No Draw")) {
            AbstractDungeon.player.getPower("No Draw").flash();
            this.isDone = true;
        } else if (this.amount <= 0) {
            this.isDone = true;
        } else {
            int deckSize = AbstractDungeon.player.drawPile.size();
            int discardSize = AbstractDungeon.player.discardPile.size();
            if (!SoulGroup.isActive()) {
                if (deckSize + discardSize == 0) {
                    this.isDone = true;
                } else if (AbstractDungeon.player.hand.size() == 10) {
                    AbstractDungeon.player.createHandIsFullDialog();
                    this.isDone = true;
                } else if (deckSize == 0) {
                    this.addToTop(new DrawUntilNotAttackCardAction());
                    this.addToTop(new EmptyDeckShuffleAction());
                    this.amount = 0;
                    this.isDone = true;
                    return;
                }
            }

            this.duration -= Gdx.graphics.getDeltaTime();
            if (this.amount != 0 && this.duration < 0.0F) {
                if (Settings.FAST_MODE) {
                    this.duration = Settings.ACTION_DUR_XFAST;
                } else {
                    this.duration = Settings.ACTION_DUR_FASTER;
                }

                --this.amount;
                if (AbstractDungeon.player.hand.size() < 10 && !AbstractDungeon.player.drawPile.isEmpty()) {
                    drawnCard = AbstractDungeon.player.drawPile.getTopCard();
                    AbstractDungeon.player.draw();
                    AbstractDungeon.player.hand.refreshHandLayout();
                    if (drawnCard.type == AbstractCard.CardType.ATTACK) {
                        this.addToBot(new DrawUntilNotAttackCardAction());
                    }
                } else {
                    logger.warn("Player attempted to draw from an empty drawpile mid-DrawAction?MASTER DECK: " + AbstractDungeon.player.masterDeck.getCardNames());
                    this.isDone = true;
                }

                if (this.amount == 0) {
                    this.isDone = true;
                }
            }

        }
    }
}


