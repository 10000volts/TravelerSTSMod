package TravelerSTSMod.Cards;

import TravelerSTSMod.Actions.StoreAction;
import TravelerSTSMod.Characters.Traveler;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Bear extends CustomCard {

    public static final String ID = "TravelerSTSMod:Bear";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "TravelerSTSModResources/img/cards/Bear.png";
    public static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = Traveler.Enums.TRAVELER_CARD;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final int COST = 1;
    private static final int DAMAGE = 10;
    private static final int MAGIC_NUMBER = 3;
    private static final int UPGRADE_MAGIC_NUMBER = 2;

    public Bear() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.damage = this.baseDamage = DAMAGE;
        this.magicNumber = this.baseMagicNumber = MAGIC_NUMBER;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int plusdamage = 0;
        for (AbstractCard c : p.drawPile.group) {
            if (c.type == CardType.CURSE || c.type == CardType.STATUS) {
                this.addToBot(new StoreAction(c, this));
                plusdamage += magicNumber;
            }
        }
        for (AbstractCard c : p.discardPile.group) {
            if (c.type == CardType.CURSE || c.type == CardType.STATUS) {
                this.addToBot(new StoreAction(c, this));
                plusdamage += magicNumber;
            }
        }
        for (AbstractCard c : p.hand.group) {
            if (c.type == CardType.CURSE || c.type == CardType.STATUS) {
                this.addToBot(new StoreAction(c, this));
                plusdamage += magicNumber;
            }
        }
        this.addToBot(new DamageAction(m, new DamageInfo(p, damage + plusdamage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MAGIC_NUMBER);
            initializeDescription();
        }
    }
}