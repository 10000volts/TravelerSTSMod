package TravelerSTSMod.Relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class CrystalFromAbyss extends CustomRelic {
    // 遗物ID
    public static final String ID = "TravelerSTSMod:CrystalFromAbyss";
    // 图片路径
    private static final String IMG_PATH = "TravelerSTSModResources/img/relics/CrystalFromAbyss.png";
    // 遗物类型
    private static final RelicTier RELIC_TIER = RelicTier.SHOP;
    // 点击音效
    private static final LandingSound LANDING_SOUND = LandingSound.SOLID;

    public CrystalFromAbyss() {
        super(ID, ImageMaster.loadImage(IMG_PATH), RELIC_TIER, LANDING_SOUND);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void atTurnStartPostDraw() {
        super.atTurnStartPostDraw();
        flash();

        addToBot(new AbstractGameAction() {
            {
                this.actionType = AbstractGameAction.ActionType.DISCARD;
                this.duration = Settings.ACTION_DUR_XFAST;
            }

            @Override
            public void update() {
                AbstractPlayer p = AbstractDungeon.player;
                int amount = 1;

                if (this.duration == Settings.ACTION_DUR_XFAST) {
                    if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                        this.isDone = true;
                        return;
                    }
                    if (p.hand.size() <= amount) {
                        this.amount = p.hand.size();
                        int tmp = p.hand.size();
                        for (int i = 0; i < tmp; i++) {
                            AbstractCard c = p.hand.getTopCard();
                            p.hand.moveToDiscardPile(c);
                            GameActionManager.incrementDiscard(false);
                        }
                        AbstractDungeon.player.hand.applyPowers();
                        tickDuration();
                        return;
                    }
                    if (p.hand.size() > amount)
                        AbstractDungeon.handCardSelectScreen.open(
                                CardCrawlGame.languagePack.getUIString("DiscardAction").TEXT[0],
                                amount, true, true);
                    p.hand.applyPowers();
                    tickDuration();
                    return;
                }
                if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                    boolean flag = false;
                    for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                        p.hand.moveToDiscardPile(c);
                        c.triggerOnManualDiscard();
                        GameActionManager.incrementDiscard(false);

                        flag = true;
                    }

                    AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;

                    if (flag) {
                        addToBot(new DamageAllEnemiesAction(null,
                                DamageInfo.createDamageMatrix(6, true),
                                DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.SLASH_HEAVY));
                    }
                }
                tickDuration();
            }
        });
    }

    public AbstractRelic makeCopy() {
        return new CrystalFromAbyss();
    }
}
