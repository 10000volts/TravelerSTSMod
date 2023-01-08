package TravelerSTSMod.Patches;

import TravelerSTSMod.Cards.Abstract.IAtBattleStart;
import TravelerSTSMod.Cards.Notes;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.screens.GameOverScreen;


@SpirePatch(clz = GameOverScreen.class, method = "calcScore", paramtypez = {boolean.class})
public class AtGameOverPatch {
    @SpirePrefixPatch
    public static void Prefix(boolean victoty) {
        Notes.monsterList.clear();
    }
}
