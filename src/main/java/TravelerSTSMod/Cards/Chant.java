package TravelerSTSMod.Cards;

import TravelerSTSMod.Cards.Abstract.ISpecificSentence;
import TravelerSTSMod.Cards.Abstract.SpellCard;
import TravelerSTSMod.Characters.Traveler;
import TravelerSTSMod.Powers.ChantPower;
import TravelerSTSMod.Powers.SentencePower;
import TravelerSTSMod.Powers.SpellIntensifyPower;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Chant extends CustomCard implements ISpecificSentence {
    public static final String ID = "TravelerSTSMod:Chant";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "TravelerSTSModResources/img/cards/Chant.png";
    public static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = Traveler.Enums.TRAVELER_CARD;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final int COST = 1;


    public Chant() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        this.isSeen = true;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Chant();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (SentencePower.getSentence(AbstractDungeon.player) == 2 ||
            (SentencePower.getSentence(AbstractDungeon.player) == 6 && this.upgraded)) {
            addToBot(new ApplyPowerAction(p, p, new ChantPower(p, p, 1), 1));
        }
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if (SentencePower.getSentence(AbstractDungeon.player) == 2 ||
                (SentencePower.getSentence(AbstractDungeon.player) == 6 && this.upgraded)) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public int getSpecificSentence(boolean general) {
        return 2;
    }
}
