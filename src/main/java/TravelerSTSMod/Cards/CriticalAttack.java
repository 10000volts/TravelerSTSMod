package TravelerSTSMod.Cards;

import TravelerSTSMod.Characters.Traveler;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
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

public class CriticalAttack extends CustomCard {
    public static final String ID = "TravelerSTSMod:CriticalAttack";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "TravelerSTSModResources/img/cards/CriticalAttack.png";
    public static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = Traveler.Enums.TRAVELER_CARD;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final int COST = 3;

    private int cardPlayedCount;


    public CriticalAttack() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        this.baseDamage = 10;
        this.damage = 10;
        this.baseBlock = 10;
        this.block = 10;
        this.cardPlayedCount = 0;
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
        CriticalAttack tmp = new CriticalAttack();
        tmp.cardPlayedCount = this.cardPlayedCount;
        return tmp;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m,
                new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL),
                AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        addToBot(new GainBlockAction(p, p, this.block));
    }

    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c) {
        if (this.cardPlayedCount == 1) {
            this.cardPlayedCount = 0;
            this.setCostForTurn(this.costForTurn - 1);
            return;
        }
        this.cardPlayedCount = 1;
    }
}