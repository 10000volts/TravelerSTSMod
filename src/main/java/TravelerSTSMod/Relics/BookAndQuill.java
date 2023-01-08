package TravelerSTSMod.Relics;

import TravelerSTSMod.Powers.BloodInkPower;
import TravelerSTSMod.Powers.SentencePower;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
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
        addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }

    public AbstractRelic makeCopy() {
        return new BookAndQuill();
    }

    public static boolean enoughInk(int n) {
        AbstractPlayer p = AbstractDungeon.player;

        if (p.hasRelic("TravelerSTSMod:AbyssInkBottle")) return true;

        int ink = 0;
        for (AbstractPower pw : p.powers) {
            if (pw.ID.equals("TravelerSTSMod:BloodInk")) {
                ink = pw.amount;
                break;
            }
        }
        for (AbstractRelic r: p.relics) {
            if (r.relicId.equals("TravelerSTSMod:BookAndQuill")) {
                ink += r.counter;
            }
        }
        return ink >= n;
    }

    public static int getInk(AbstractPlayer p) {
        int ret = 0;
        for (AbstractPower pw : p.powers) {
            if (pw.ID.equals("TravelerSTSMod:BloodInk")) {
                ret = pw.amount;
                break;
            }
        }
        for (AbstractRelic r: p.relics) {
            if (r.relicId.equals("TravelerSTSMod:BookAndQuill")) {
                return ret + r.counter;
            }
        }
        return ret;
    }

    public static void increaseInk(AbstractPlayer p, int n) {
        for (AbstractRelic r: p.relics) {
            if (r.relicId.equals("TravelerSTSMod:BookAndQuill")) {
                r.flash();
                r.counter += n;
                return;
            }
        }
    }

    public static boolean costInk(AbstractPlayer p, int cost) {
        int bloodInkAmount = 0;
        AbstractPower bloodInk = null;

        for (AbstractPower pw : p.powers) {
            if (pw.ID.equals("TravelerSTSMod:BloodInk")) {
                bloodInk = pw;
                bloodInkAmount = pw.amount;
                if (bloodInkAmount >= cost) {
                    pw.amount -= cost;
                    if (pw.amount == 0) {
                        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(
                                pw.owner, pw.owner, "TravelerSTSMod:BloodInk"));
                    }
                    return true;
                }
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(
                        bloodInk.owner, bloodInk.owner, "TravelerSTSMod:BloodInk"));
                break;
            }
        }

        for (AbstractRelic r: p.relics) {
            if (r.relicId.equals("TravelerSTSMod:BookAndQuill")) {
                if (cost <= bloodInkAmount + r.counter) {
                    r.counter -= cost - bloodInkAmount;
                    return true;
                } else if (p.hasRelic("TravelerSTSMod:AbyssInkBottle")) {
                    int hpcost = cost - bloodInkAmount - r.counter;
                    r.counter = 0;
                    AbstractDungeon.actionManager.addToBottom(new LoseHPAction(p, p, hpcost));
                    return true;
                }
                return false;
            }
        }
        return false;
    }
}
