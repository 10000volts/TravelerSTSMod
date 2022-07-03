package TravelerSTSMod.Cards;

import TravelerSTSMod.Characters.Traveler;
import TravelerSTSMod.ModCore.TravelerMod;
import TravelerSTSMod.Powers.SentencePower;
import TravelerSTSMod.Powers.WhisperPower;
import TravelerSTSMod.Relics.BookAndQuill;
import basemod.abstracts.CustomCard;
import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;

import java.util.ArrayList;

public class Notes extends CustomCard implements CustomSavable<ArrayList<String>> {
    public static final String ID = "TravelerSTSMod:Notes";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "TravelerSTSModResources/img/cards/Notes.png";
    public static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = Traveler.Enums.TRAVELER_CARD;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final int COST = 1;

    public static ArrayList<String> monsterList = new ArrayList<>();

    public Notes() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        this.isSeen = true;

        this.baseDamage = 50;
        this.damage = 50;
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (monsterList.contains(m.id)) {
            addToBot(new VFXAction(new WeightyImpactEffect(m.hb.cX, m.hb.cY, TravelerMod.MY_COLOR)));
            addToBot(new DamageAction(m,
                    new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL),
                    AbstractGameAction.AttackEffect.NONE));
            BookAndQuill.increaseInk(p, 1);
        } else {
            monsterList.add(m.id);
        }
    }

    public AbstractCard makeCopy() {
        return new Notes();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(10);
        }
    }

    @Override
    public ArrayList<String> onSave() {
        return monsterList;
    }

    @Override
    public void onLoad(ArrayList<String> strings) {
        monsterList.clear();
        monsterList.addAll(strings);
    }
}
