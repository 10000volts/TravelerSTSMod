package TravelerSTSMod.Patches;

import TravelerSTSMod.Cards.Abstract.IReboundCard;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

@SpirePatch(clz = UseCardAction.class, method = "update")
public class GhostPatch {
    public static ExprEditor Instrument() {
        return new ExprEditor() {
            public void edit(MethodCall m) throws CannotCompileException {
                if (m.getClassName().equals(CardGroup.class.getName()) &&
                        m.getMethodName().equals("moveToDiscardPile")) {
                    m.replace("if (" + GhostPatch.class.getName() + ".Do($1)) {$_ = $proceed($$);}");
                }
            }
        };
    }

    public static boolean Do(AbstractCard card) {
        if (card instanceof IReboundCard) {
            AbstractDungeon.player.hand.moveToDeck(card, false);
            return false;
        }
        return true;
    }
}
