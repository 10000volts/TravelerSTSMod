package TravelerSTSMod.Cards;

import TravelerSTSMod.Actions.SelectCardAction;
import TravelerSTSMod.Actions.StoreAction;
import TravelerSTSMod.Cards.Abstract.IStoredCard;
import TravelerSTSMod.Characters.Traveler;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Flashback extends CustomCard implements IStoredCard {

    public static final String ID = "TravelerSTSMod:Flashback";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "TravelerSTSModResources/img/cards/Flashback.png";
    public static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = Traveler.Enums.TRAVELER_CARD;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final int COST = 1;
    private static final int DAMAGE = 8;
    private static final int UPGRADE_PLUS_DMG = 3;

    public Flashback() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.damage = this.baseDamage = DAMAGE;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAllEnemiesAction(p, this.damage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
        this.addToBot(new SelectCardAction("寄存", p.hand, 1, null, cards -> this.addToBot(new StoreAction(cards, this)), false, false));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            initializeDescription();
        }
    }

    @Override
    public void triggerWhenStored() {
        AbstractPlayer p = AbstractDungeon.player;
        this.addToBot(new DamageAllEnemiesAction(p, this.damage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
    }
}