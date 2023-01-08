package TravelerSTSMod.Patches;

import TravelerSTSMod.Characters.Traveler;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.relics.Enchiridion;

@SpirePatch(clz = Enchiridion.class, method = "atPreBattle", paramtypez =
        {})
public class EnchiridionPatch {
    @SpireInsertPatch(rloc = 6, localvars = {"c"})
    public static void Insert(Enchiridion self, AbstractCard c) {
        if (c.tags.contains(Traveler.Enums.TRAVELER_SPELL)) {
            c.modifyCostForCombat(-9);
        }
    }
}
