package TravelerSTSMod.Relics;

import TravelerSTSMod.Actions.RandomCardForFreeAction;
import TravelerSTSMod.Powers.SentencePower;
import TravelerSTSMod.Powers.WhisperPower;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class InkHourglass extends CustomRelic implements SentencePower.IOnSentenceChanged {
    // 遗物ID
    public static final String ID = "TravelerSTSMod:InkHourglass";
    // 图片路径
    private static final String IMG_PATH = "TravelerSTSModResources/img/relics/InkHourglass.png";
    // 遗物类型
    private static final RelicTier RELIC_TIER = RelicTier.UNCOMMON;
    // 点击音效
    private static final LandingSound LANDING_SOUND = LandingSound.SOLID;

    public InkHourglass() {
        super(ID, ImageMaster.loadImage(IMG_PATH), RELIC_TIER, LANDING_SOUND);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy() {
        return new InkHourglass();
    }

    @Override
    public void onSentenceIncreased(AbstractPower sender, int sb, int sa) {
        if (sa >= 5 && sb < 5) {
            flash();
            AbstractPlayer p = AbstractDungeon.player;
            addToBot(new RelicAboveCreatureAction(p, this));
            addToBot(new DamageAllEnemiesAction(null,
                    DamageInfo.createDamageMatrix(5, true),
                    DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.SLASH_HEAVY));
        }
    }
}
