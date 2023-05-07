package TravelerSTSMod.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class ParasitesAction extends AbstractGameAction {
    public int[] damage;
    private final boolean freeToPlayOnce;
    private final DamageInfo.DamageType damageType;
    private final AbstractPlayer p;
    private final AbstractCard c;
    private final int energyOnUse;
    private boolean kill = false;

    public ParasitesAction(AbstractPlayer p, AbstractCard card, int[] damage, DamageInfo.DamageType damageType, boolean freeToPlayOnce, int energyOnUse) {
        this.damage = damage;
        this.damageType = damageType;
        this.p = p;
        this.c = card;
        this.freeToPlayOnce = freeToPlayOnce;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
    }

    public void update() {
        int tmp = AbstractDungeon.getCurrRoom().monsters.monsters.size();
        if (this.duration == Settings.ACTION_DUR_XFAST) {
            int effect = EnergyPanel.totalCount;
            if (this.energyOnUse != -1) {
                effect = this.energyOnUse;
            }
            if (this.p.hasRelic("Chemical X")) {
                effect += 2;
                this.p.getRelic("Chemical X").flash();
            }
            if (!this.freeToPlayOnce) {
                this.p.energy.use(EnergyPanel.totalCount);
            }
            for (AbstractPower power : this.p.powers) {
                power.onDamageAllEnemies(this.damage);
            }
            for (int i = 0; i < effect; ++i) {
                for (int j = 0; j < tmp; ++j) {
                    AbstractMonster m = AbstractDungeon.getCurrRoom().monsters.monsters.get(j);
                    if (!m.isDeadOrEscaped()) {
                        m.damage(new DamageInfo(this.p, this.damage[j], this.damageType));
                        AbstractDungeon.effectList.add(new FlashAtkImgEffect(m.hb.cX, m.hb.cY, this.attackEffect));
                        if ((m.isDying || m.currentHealth <= 0) && !m.halfDead) {
                            kill = true;
                        }
                    }
                }
            }
        }
        if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
            AbstractDungeon.actionManager.clearPostCombatActions();
        }
        if (kill) {
            this.addToBot(new SelectCardAction("作为容器", p.discardPile, 1, card -> !card.equals(this.c), cards -> this.addToTop(new StoreAction(this.c, cards.get(0)))));
        }

        this.tickDuration();
        this.isDone = true;
    }
}
