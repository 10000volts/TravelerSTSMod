package TravelerSTSMod.Patches;

import TravelerSTSMod.Cards.Abstract.PersonalityCard;
import TravelerSTSMod.Characters.Traveler;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

@SpirePatch(clz = AbstractPlayer.class, method = "useCard", paramtypez =
        {AbstractCard.class, AbstractMonster.class, int.class})
public class SpellStormPatch {
    public static int spellUsed = 0;

    @SpireInsertPatch(rloc = 17)
    public static void Insert(AbstractPlayer self, AbstractCard c, AbstractMonster m, int e) {
        if (c.tags.contains(Traveler.Enums.TRAVELER_SPELL)) {
            spellUsed += 1;
        }
    }
}
