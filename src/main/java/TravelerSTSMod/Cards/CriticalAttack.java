package TravelerSTSMod.Cards;

import TravelerSTSMod.Cards.Abstract.IAtTurnStartWherever;
import TravelerSTSMod.Characters.Traveler;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ClashEffect;

public class CriticalAttack extends CustomCard implements IAtTurnStartWherever {
    public static final String ID = "TravelerSTSMod:CriticalAttack";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "TravelerSTSModResources/img/cards/CriticalAttack.png";
    public static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = Traveler.Enums.TRAVELER_CARD;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    public static final int COST = 3;

    private int cardPlayedCount;
    private int costInfluenced;
    private int costTemp;

    public CriticalAttack(int cardPlayedCount, int costInfluenced, int costTemp) {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        this.isSeen = true;

        this.baseDamage = 10;
        this.damage = 10;
        this.baseBlock = 10;
        this.block = 10;

        this.cardPlayedCount = cardPlayedCount;
        this.costInfluenced = costInfluenced;
        this.costTemp = costTemp;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(2);
            this.upgradeBlock(2);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new CriticalAttack(this.cardPlayedCount, this.costInfluenced, this.costTemp);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null)
            addToBot(new VFXAction(new ClashEffect(m.hb.cX, m.hb.cY), 0.1F));
        addToBot(new DamageAction(m,
                new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL),
                AbstractGameAction.AttackEffect.NONE));
        addToBot(new GainBlockAction(p, p, this.block));
    }

    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c) {
        if (this.cardPlayedCount == 1) {
            this.cardPlayedCount = 0;
            if (this.costForTurn > 0) {
//                this.costTemp -= 1;
//                this.costInfluenced += 1;
                this.setCostForTurn(this.costForTurn - 1);
                // this.updateCost(-1);
            }
            return;
        }
        this.cardPlayedCount = 1;
    }

    @Override
    public void atTurnStartWherever() {
//        if (this.costTemp != this.cost) {
//            this.costTemp = this.cost;
//        } else {
//            this.updateCost(this.costInfluenced);
//        }
//        this.costInfluenced = 0;
        this.cardPlayedCount = 0;
    }
}
