package TravelerSTSMod.Patches;

import TravelerSTSMod.Cards.Inking;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;

@SpirePatch(clz = AbstractCreature.class, method = "decrementBlock", paramtypez =
        {DamageInfo.class, int.class})
public class AbsorbSpellPatch {
    @SpireInsertPatch(rloc = 4)
    public static void Insert(AbstractCreature self, DamageInfo info, int d) {
        if (info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS &&
                info.owner != null && info.owner != self) {
            for (AbstractPower pw : self.powers) {
                if (pw.ID.equals("TravelerSTSMod:AbsorbSpell")) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(pw.owner, pw.owner,
                            new NextTurnBlockPower(pw.owner,
                                    Math.min(d, pw.owner.currentBlock),
                                    pw.name), Math.min(d, pw.owner.currentBlock)));
                    pw.flash();
                    return;
                }
            }
        }
    }
}
