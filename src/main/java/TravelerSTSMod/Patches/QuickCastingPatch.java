package TravelerSTSMod.Patches;

import TravelerSTSMod.Characters.Traveler;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

@SpirePatch(clz = UseCardAction.class, method = SpirePatch.CONSTRUCTOR,
        paramtypez = {AbstractCard.class, AbstractCreature.class})
public class QuickCastingPatch {

    @SpireInsertPatch(rloc = 3)
    public static void Insert(UseCardAction self, AbstractCard card, AbstractCreature target) {
        if (AbstractDungeon.player.hasPower("TravelerSTSMod:QuickCasting") &&
                card.hasTag(Traveler.Enums.TRAVELER_SPELL)) {
            self.exhaustCard = true;
        }
    }
}
