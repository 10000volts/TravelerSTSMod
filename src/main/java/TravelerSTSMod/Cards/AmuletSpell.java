package TravelerSTSMod.Cards;

import TravelerSTSMod.Cards.Abstract.ISpecificSentence;
import TravelerSTSMod.Cards.Abstract.SpellCard;
import TravelerSTSMod.Characters.Traveler;
import TravelerSTSMod.ModCore.TravelerMod;
import TravelerSTSMod.Powers.ChantPower;
import TravelerSTSMod.Powers.SentencePower;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ReduceCostAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class AmuletSpell extends SpellCard implements SentencePower.IOnSentenceChanged, ISpecificSentence {
    public static final String ID = "TravelerSTSMod:AmuletSpell";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "TravelerSTSModResources/img/cards/AmuletSpell.png";
    public static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = Traveler.Enums.TRAVELER_CARD;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final int COST = 1;
    private int costDecrease;

    public AmuletSpell(int influenced) {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, influenced);

        this.isSeen = true;

        this.baseDamage = 6;
        this.damage = 6;
        this.costDecrease = 0;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(3);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        AmuletSpell tmp = new AmuletSpell(this.costInfluencedLastTurn);
        if (CardCrawlGame.dungeon != null && AbstractDungeon.currMapNode != null &&
                (AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT) {
            tmp.costDecrease = this.costDecrease;
            tmp.updateCost(this.cost);
            System.out.println(tmp.cost);
        }
        return tmp;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m,
                new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL),
                AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }


    @Override
    public void updateCost(int amt) {
        int tmpCost = 0;
        int diff = this.cost - this.costForTurn;
        tmpCost += amt;
        if (tmpCost <= 0)
            tmpCost = 0;
//        if (AbstractDungeon.player.hasPower(ChantPower.ID)) {
//            this.setCostForTurn(COST - 1);
//        }
        else {
            for (AbstractPower pw: AbstractDungeon.player.powers) {
                if (pw.ID.equals(QuickCasting.ID)) {
                    tmpCost = 0;
                    this.costInfluencedLastTurn = 0;
                }
                else if (pw.ID.equals(ChantPower.POWER_ID)) {
                    int m = Math.min(tmpCost, pw.amount);
                    tmpCost -= m;
                    this.costInfluencedLastTurn += m;
                    break;
                }
            }
            if (tmpCost <= 0)
                tmpCost = 0;
            else {
                int d = Math.min(tmpCost, this.costDecrease);
                tmpCost -= d;
                this.costInfluencedLastTurn += d;
                this.costDecrease = 0;
            }
        }
        if (tmpCost != this.cost) {
            this.isCostModified = true;
            this.cost = tmpCost;
            this.costForTurn = this.cost - diff;
            if (this.costForTurn < 0)
                this.costForTurn = 0;
        }
    }

    @Override
    public void onSentenceIncreased(AbstractPower sender, int sb, int s) {
        if (s >= 5 && sb < 5) {
            addToBot(new DiscardToHandAction(this));
            this.costDecrease += 1;
            this.updateCost(this.cost);
        }
    }

    @Override
    public int getSpecificSentence(boolean general) {
        return general ? 5 : -1;
    }
}
