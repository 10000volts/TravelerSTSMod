package TravelerSTSMod.Patches;

import TravelerSTSMod.Cards.Abstract.PersonalityCard;
import TravelerSTSMod.Cards.QuickCasting;
import TravelerSTSMod.Characters.Traveler;
import TravelerSTSMod.Relics.SoulVessel;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ConfusionPower;

import java.util.ArrayList;

@SpirePatch(clz = ConfusionPower.class, method = "onCardDraw", paramtypez =
        {AbstractCard.class})
public class ConfusionPowerPatch {
    @SpirePrefixPatch
    public static SpireReturn<Void> Prefix(ConfusionPower self, AbstractCard c) {
        if (c.tags.contains(Traveler.Enums.TRAVELER_SPELL) && AbstractDungeon.player.hasPower(QuickCasting.ID)) {
            return SpireReturn.Return(null);
        }
        return SpireReturn.Continue();
    }
}
