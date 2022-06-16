package TravelerSTSMod.Relics;

import TravelerSTSMod.Powers.SentencePower;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class BookAndQuill extends CustomRelic {
    // 遗物ID
    public static final String ID = "TravelerSTSMod:BookAndQuill";
    // 图片路径
    private static final String IMG_PATH = "TravelerSTSModResources/img/relics/BookAndQuill.png";
    // 遗物类型
    private static final AbstractRelic.RelicTier RELIC_TIER = AbstractRelic.RelicTier.STARTER;
    // 点击音效
    private static final AbstractRelic.LandingSound LANDING_SOUND = AbstractRelic.LandingSound.FLAT;

    public BookAndQuill() {
        super(ID, ImageMaster.loadImage(IMG_PATH), RELIC_TIER, LANDING_SOUND);
        // 笔墨
        this.counter = 0;
    }

    // 获取遗物描述，但原版游戏只在初始化和获取遗物时调用，故该方法等于初始描述
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void onEnterRoom(AbstractRoom room) {
        flash();
        this.counter += 1;
    }

    public void atBattleStart() {
        addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                new SentencePower(AbstractDungeon.player), 1));
        addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }

    public AbstractRelic makeCopy() {
        return new BookAndQuill();
    }

    public static int getInk(AbstractPlayer p) {
        for (AbstractRelic r: p.relics) {
            if (r.relicId.equals("TravelerSTSMod:BookAndQuill")) {
                return r.counter;
            }
        }
        return 0;
    }

    public static boolean costInk(AbstractPlayer p, int cost) {
        for (AbstractRelic r: p.relics) {
            if (r.relicId.equals("TravelerSTSMod:BookAndQuill")) {
                if (cost <= r.counter) {
                    r.counter -= cost;
                    return true;
                }
                return false;
            }
        }
        return false;
    }
}
