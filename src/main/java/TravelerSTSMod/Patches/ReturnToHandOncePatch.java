package TravelerSTSMod.Patches;

import TravelerSTSMod.Characters.Traveler;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.lang.reflect.Field;
import java.util.*;

@SpirePatch(clz = UseCardAction.class, method = "update", paramtypez =
        {})
public class ReturnToHandOncePatch {
    public static HashMap<AbstractCard, Boolean> returnToHandOnceField = new HashMap<>();

    @SpireInsertPatch(rloc = 155 - 84)
    public static void Insert(UseCardAction self) {
        try {
            Field cField = UseCardAction.class.getDeclaredField("targetCard");
            cField.setAccessible(true);
            AbstractCard c = (AbstractCard) cField.get(self);
            if (returnToHandOnceField.getOrDefault(c, false)) {
                c.returnToHand = false;
                returnToHandOnceField.remove(c);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace(System.out);
        }
    }
}
