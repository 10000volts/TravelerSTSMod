package TravelerSTSMod.Patches;

import TravelerSTSMod.Cards.Abstract.IReboundCard;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;

@SpirePatch(clz = UseCardAction.class, method = "update")
public class ReturnToHandPatch {
    public static ExprEditor Instrument() {
        return new ExprEditor() {
            public void edit(FieldAccess f) throws CannotCompileException {
                if (f.getClassName().equals(AbstractCard.class.getName()) &&
                        f.getFieldName().equals("returnToHand")) {
                    f.replace("$_ = $proceed() && " + AbstractDungeon.class.getName() +
                            ".player.hand.size() < 10;");
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
