package TravelerSTSMod.Cards.Abstract;

import TravelerSTSMod.Characters.Traveler;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;

public abstract class PersonalityCard extends CustomCard {
    public static final Color P_BORDER_GLOW_COLOR = new Color(240.0F / 255.0F, 36.0F / 255.0F, 143.0F / 255.0F, 1.0F);
    public static final Color P_BORDER_GLOW_COLOR2 = new Color(249.0F / 255.0F, 236.0F / 255.0F, 255.0F / 255.0F, 1.0F);

    public PersonalityCard(String ID, String NAME, String IMG_PATH, int COST, String DESCRIPTION,
                           CardType TYPE, CardColor COLOR, CardRarity RARITY, CardTarget TARGET, boolean ethereal) {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        this.isEthereal = ethereal;
        this.tags.add(Traveler.Enums.TRAVELER_PERSONALITY);
    }

    public void onCheckAct(AbstractCard c, AbstractMonster m) {
        ArrayList<AbstractCard> g = AbstractDungeon.player.hand.group;
        // 可能在触发时已不在手牌
        if (!g.contains(c)) return;

        int i = g.indexOf(c);
        int i_this = g.indexOf(this);
        boolean onLeft = (i_this < i);
        // 触发区间
        ArrayList<AbstractCard> cs = new ArrayList<>();

        boolean flag = false;
        int ii = i-1;
        // 找到在c左侧的，离c最近的人格卡
        for (; ii >= 0; --ii) {
            AbstractCard c_temp = g.get(ii);
            if(c_temp instanceof PersonalityCard) {
                // 这张人格卡不是离c最近的人格卡
                if (onLeft && !c_temp.equals(this)) {
                    return;
                }
                flag = true;
                break;
            } else {
                cs.add(c_temp);
            }
        }
        // c左侧不存在人格卡，不可能满足人格触发条件
        if (!flag) return;
        flag = false;

        // 找到在c右侧的，离c最近的人格卡
        ii = i+1;
        for (; ii < g.size(); ++ii) {
            AbstractCard c_temp = g.get(ii);
            if(c_temp instanceof PersonalityCard) {
                if (!onLeft && !c_temp.equals(this)) {
                    return;
                }
                flag = true;
                break;
            } else {
                cs.add(c_temp);
            }
        }
        if (!flag) return;

        for (AbstractPower pw : AbstractDungeon.player.powers) {
            if (pw.ID.equals("TravelerSTSMod:Degenerate")) {
                for (int j=0; j<pw.amount; ++j) {
                    onAct(c, cs, m);
                }
            }
        }
        onAct(c, cs, m);
        if (c instanceof IOnTriggerAction) {
            ((IOnTriggerAction)c).onTriggerAction(i);
        }
    }

    // c: 触发行动的卡 cards: 触发区间(不包括c)
    public void onAct(AbstractCard c, ArrayList<AbstractCard> cards, AbstractMonster m) {
        this.superFlash();
    }

    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        for (AbstractCard c: AbstractDungeon.player.hand.group) {
            if (c.tags.contains(Traveler.Enums.TRAVELER_PERSONALITY) && !c.equals(this)) {
                this.glowColor = P_BORDER_GLOW_COLOR.cpy();
                return;
            }
        }
    }
}
