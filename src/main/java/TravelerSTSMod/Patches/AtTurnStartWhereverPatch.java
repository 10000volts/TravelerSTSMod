package TravelerSTSMod.Patches;

import TravelerSTSMod.Cards.Abstract.IAtTurnStartWherever;
import TravelerSTSMod.Cards.Notes;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.screens.GameOverScreen;


@SpirePatch(clz = AbstractPlayer.class, method = "applyStartOfTurnPreDrawCards", paramtypez = {})
public class AtTurnStartWhereverPatch {
    @SpirePrefixPatch
    public static void Prefix(AbstractPlayer self) {
        for (AbstractCard c : self.hand.group) {
            if (c instanceof IAtTurnStartWherever) {
                ((IAtTurnStartWherever) c).atTurnStartWherever();
            }
        }
        for (AbstractCard c : self.drawPile.group) {
            if (c instanceof IAtTurnStartWherever) {
                ((IAtTurnStartWherever) c).atTurnStartWherever();
            }
        }
        for (AbstractCard c : self.discardPile.group) {
            if (c instanceof IAtTurnStartWherever) {
                ((IAtTurnStartWherever) c).atTurnStartWherever();
            }
        }
        for (AbstractCard c : self.exhaustPile.group) {
            if (c instanceof IAtTurnStartWherever) {
                ((IAtTurnStartWherever) c).atTurnStartWherever();
            }
        }
    }
}
