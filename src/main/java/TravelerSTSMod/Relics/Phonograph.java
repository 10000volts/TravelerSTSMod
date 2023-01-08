package TravelerSTSMod.Relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class Phonograph extends CustomRelic {
    // 遗物ID
    public static final String ID = "TravelerSTSMod:Phonograph";
    // 图片路径
    private static final String IMG_PATH = "TravelerSTSModResources/img/relics/Phonograph.png";
    // 遗物类型
    private static final RelicTier RELIC_TIER = RelicTier.RARE;
    // 点击音效
    private static final LandingSound LANDING_SOUND = LandingSound.SOLID;

    public Phonograph() {
        super(ID, ImageMaster.loadImage(IMG_PATH), RELIC_TIER, LANDING_SOUND);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy() {
        return new Phonograph();
    }
}
