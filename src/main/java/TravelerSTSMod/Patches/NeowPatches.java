package TravelerSTSMod.Patches;

import TravelerSTSMod.Characters.Traveler;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.curses.Clumsy;
import com.megacrit.cardcrawl.cards.curses.Parasite;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.neow.NeowReward;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import java.util.ArrayList;
import java.util.function.Predicate;

public class NeowPatches {
    @SpireEnum
    public static NeowReward.NeowRewardType TRAVELER_4;

    @SpirePatch(clz = NeowReward.class, method = "getRewardOptions")
    public static class AddRewards {

        public static final String TEXT = CardCrawlGame.languagePack.
                getCharacterString("TravelerSTSMod:Neow").TEXT[0];

        @SpirePostfixPatch
        public static ArrayList<NeowReward.NeowRewardDef> Insert (ArrayList<NeowReward.NeowRewardDef> __result,
                                                                  NeowReward self, int category) {
            if (category == 3 && AbstractDungeon.player.chosenClass == Traveler.Enums.TRAVELER) {
                // 删除原有的4选项并替换
                __result.removeIf(neowRewardDef -> neowRewardDef.type == NeowReward.NeowRewardType.BOSS_RELIC);
                __result.add(new NeowReward.NeowRewardDef(TRAVELER_4, TEXT));
            }
            return __result;
        }
    }

    @SpirePatch(clz = NeowReward.class, method = "activate")
    public static class ActivatePatch {
        @SpirePrefixPatch
        public static void patch(NeowReward __instance) {
            if (__instance.type == TRAVELER_4) {
                AbstractDungeon.topLevelEffects.add(new ShowCardAndObtainEffect(
                        new Parasite().makeCopy(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain((Settings.WIDTH / 2), (Settings.HEIGHT / 2),
                        AbstractDungeon.returnRandomRelic(AbstractRelic.RelicTier.BOSS));
                AbstractDungeon.topLevelEffects.add(new ShowCardAndObtainEffect(
                        new Clumsy().makeCopy(), Settings.WIDTH / 2.0F - 200, Settings.HEIGHT / 2.0F));
            }
        }
    }
}
