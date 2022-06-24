package TravelerSTSMod.ModCore;
import TravelerSTSMod.Cards.*;
import TravelerSTSMod.Characters.Traveler;
import TravelerSTSMod.Patches.SpellStormPatch;
import TravelerSTSMod.Relics.AbyssInkBottle;
import TravelerSTSMod.Relics.BlankMovement;
import TravelerSTSMod.Relics.BookAndQuill;
import TravelerSTSMod.Relics.MobiusBand;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import basemod.BaseMod;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.*;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

@SpireInitializer
public class TravelerMod implements EditCharactersSubscriber,EditCardsSubscriber,
        EditRelicsSubscriber, EditStringsSubscriber, EditKeywordsSubscriber, PostBattleSubscriber {
    public static final Logger logger = LogManager.getLogger(TravelerMod.class);

    // 人物选择界面按钮的图片
    private static final String MY_CHARACTER_BUTTON = "TravelerSTSModResources/img/charSelect/button.png";
    // 人物选择界面的立绘
    private static final String MY_CHARACTER_PORTRAIT = "TravelerSTSModResources/img/charSelect/travelerPortrait.png";
    // 攻击牌的背景（小尺寸）
    private static final String BG_ATTACK_512 = "TravelerSTSModResources/img/512/bg_attack.png";
    // 能力牌的背景（小尺寸）
    private static final String BG_POWER_512 = "TravelerSTSModResources/img/512/bg_power.png";
    // 技能牌的背景（小尺寸）
    private static final String BG_SKILL_512 = "TravelerSTSModResources/img/512/bg_skill.png";
    // 在卡牌和遗物描述中的能量图标
    private static final String SMALL_ORB = "TravelerSTSModResources/img/char/small_orb.png";
    // 攻击牌的背景（大尺寸）
    private static final String BG_ATTACK_1024 = "TravelerSTSModResources/img/1024/bg_attack.png";
    // 能力牌的背景（大尺寸）
    private static final String BG_POWER_1024 = "TravelerSTSModResources/img/1024/bg_power.png";
    // 技能牌的背景（大尺寸）
    private static final String BG_SKILL_1024 = "TravelerSTSModResources/img/1024/bg_skill.png";
    // 在卡牌预览界面的能量图标
    private static final String BIG_ORB = "TravelerSTSModResources/img/char/card_orb.png";
    // 小尺寸的能量图标（战斗中，牌堆预览）
    private static final String ENEYGY_ORB = "TravelerSTSModResources/img/char/cost_orb.png";
    public static final Color MY_COLOR = new Color(103.0F / 255.0F, 83.0F / 255.0F, 161.0F / 255.0F, 1.0F);

    public static ArrayList<AbstractCard> personalityPool;
    public static ArrayList<AbstractCard> spellPool;

    static {
        personalityPool = new ArrayList<>();
        spellPool = new ArrayList<>();
    }

    public TravelerMod() {
        BaseMod.subscribe(this);
    }

    public static void initialize() {
        new TravelerMod();
    }


    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new Traveler(CardCrawlGame.playerName), MY_CHARACTER_BUTTON, MY_CHARACTER_PORTRAIT,
                Traveler.Enums.TRAVELER);
    }

    @Override
    public void receiveEditRelics() {
        BaseMod.addRelicToCustomPool(new BookAndQuill(), Traveler.Enums.TRAVELER_CARD);
        BaseMod.addRelicToCustomPool(new BlankMovement(), Traveler.Enums.TRAVELER_CARD);
        BaseMod.addRelicToCustomPool(new AbyssInkBottle(), Traveler.Enums.TRAVELER_CARD);
        BaseMod.addRelicToCustomPool(new MobiusBand(), Traveler.Enums.TRAVELER_CARD);
    }

    private void addPersonalities() {
        personalityPool.add(new Wrath(false));
        personalityPool.add(new Gluttony(false));
        personalityPool.add(new Lust(false));
        personalityPool.add(new Greed(false));
        personalityPool.add(new Sloth(false));
        personalityPool.add(new Envy(false));
        personalityPool.add(new PrideForm(false));
    }

    private void addSpells() {
        spellPool.add(new PainSpell(0));
        spellPool.add(new AmuletSpell(0));
        spellPool.add(new BarrierSpell(0));
        spellPool.add(new RigmaroleSpell(0));
        spellPool.add(new SpellStorm(0));
        spellPool.add(new ShuttleSpell(0));
        spellPool.add(new SurgeSpell(0));
        spellPool.add(new VajraSpell(0));
        spellPool.add(new ExplodeSpell(0));
        spellPool.add(new ContractSpell(0));
        spellPool.add(new SpellSearch(0));
        spellPool.add(new QuickSpell(0));
        spellPool.add(new SpellAmplify(0));
        spellPool.add(new EchoSpell(0));
        spellPool.add(new TemblorSpell(0));
        spellPool.add(new SpellIntensify(0));
        spellPool.add(new AbsorbSpell(0));
    }

    @Override
    public void receiveEditCards() {
        addPersonalities();
        addSpells();

        BaseMod.addCard(new Strike());
        BaseMod.addCard(new InkDrop());
        BaseMod.addCard(new Defend());
        BaseMod.addCard(new EmptySpell(0));
        BaseMod.addCard(new PainSpell(0));
        BaseMod.addCard(new SuddenStrike());
        BaseMod.addCard(new AmuletSpell(0));
        BaseMod.addCard(new HeartLock());
        BaseMod.addCard(new Melody());
        BaseMod.addCard(new BarrierSpell(0));
        BaseMod.addCard(new DistortingStrike());
        BaseMod.addCard(new FromAbyss());
        BaseMod.addCard(new Greed(false));
        BaseMod.addCard(new MindShock());
        BaseMod.addCard(new MindSnoop());
        BaseMod.addCard(new RigmaroleSpell(0));
        BaseMod.addCard(new Somniloquy());
        BaseMod.addCard(new Swallow());
        BaseMod.addCard(new Ghost());
        BaseMod.addCard(new SpellStorm(0));
        BaseMod.addCard(new Alchemy());
        BaseMod.addCard(new BirthSpell(0));
        BaseMod.addCard(new Envy(false));
        BaseMod.addCard(new Hypnotize());
        BaseMod.addCard(new InkSplash());
        BaseMod.addCard(new ShuttleSpell(0));
        BaseMod.addCard(new Sloth(false));
        BaseMod.addCard(new Solidify());
        BaseMod.addCard(new SurgeSpell(0));
        BaseMod.addCard(new TurnPages());
        BaseMod.addCard(new VajraSpell(0));
        BaseMod.addCard(new VoidBelief());
        BaseMod.addCard(new Wrath(false));
        BaseMod.addCard(new ExplodeSpell(0));
        BaseMod.addCard(new CriticalAttack());
        BaseMod.addCard(new LiteraryTalent());
        BaseMod.addCard(new ContractSpell(0));
        BaseMod.addCard(new Transcend());
        BaseMod.addCard(new Wisdom());
        BaseMod.addCard(new Vacillate());
        BaseMod.addCard(new Organize());
        BaseMod.addCard(new Paranoid());
        BaseMod.addCard(new SpellSearch(0));
        BaseMod.addCard(new QuickSpell(0));
        BaseMod.addCard(new Lust(false));
        BaseMod.addCard(new ThinkHard());
        BaseMod.addCard(new Recount());
        BaseMod.addCard(new Degenerate());
        BaseMod.addCard(new Gluttony(false));
        BaseMod.addCard(new SpellAmplify(0));
        BaseMod.addCard(new VoiceOfDark());
        BaseMod.addCard(new EchoSpell(0));
        BaseMod.addCard(new Inspire());
        BaseMod.addCard(new Palindrome());
        BaseMod.addCard(new TimePassed());
        BaseMod.addCard(new TemblorSpell(0));
        BaseMod.addCard(new Tribute());
        BaseMod.addCard(new Recall());
        BaseMod.addCard(new Chant());
        BaseMod.addCard(new SpellIntensify(0));
        BaseMod.addCard(new GazeOfAbyss());
        BaseMod.addCard(new Inking());
        BaseMod.addCard(new Illusion());
        BaseMod.addCard(new Squeeze());
        BaseMod.addCard(new AbsorbSpell(0));

        BaseMod.addCard(new BadOmen());
        BaseMod.addCard(new SpellKit(0));
        BaseMod.addCard(new PrideForm(false));
        BaseMod.addCard(new QuickCasting());
        BaseMod.addCard(new MindSplit());
    }

    @Override
    public void receiveEditStrings() {
//        switch (Settings.language){
//        }
        String llPath = "TravelerSTSModResources/localization/";
        String lang = "ZHS";
        String cardStrings = Gdx.files.internal(llPath + lang + "/card.json").readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(CardStrings.class, cardStrings);
        String relicStrings = Gdx.files.internal(llPath + lang + "/relic.json").readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(RelicStrings.class, relicStrings);
        String charStrings = Gdx.files.internal(llPath + lang + "/characters.json").readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(CharacterStrings.class, charStrings);
        String powerStrings = Gdx.files.internal(llPath + lang + "/power.json").readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(PowerStrings.class, powerStrings);
        String uiStrings = Gdx.files.internal(llPath + lang + "/uiString.json").readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(UIStrings.class, uiStrings);

        BaseMod.addColor(Traveler.Enums.TRAVELER_CARD, MY_COLOR, MY_COLOR, MY_COLOR, MY_COLOR, MY_COLOR, MY_COLOR, MY_COLOR,
                BG_ATTACK_512,BG_SKILL_512,BG_POWER_512,ENEYGY_ORB,BG_ATTACK_1024,BG_SKILL_1024,BG_POWER_1024,BIG_ORB,
                SMALL_ORB);
    }

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String llPath = "TravelerSTSModResources/localization/";
        String lang = "ZHS";

        String json = Gdx.files.internal(llPath + lang + "/keyword.json")
                .readString(String.valueOf(StandardCharsets.UTF_8));
        Keyword[] keywords = gson.fromJson(json, Keyword[].class);
        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword("travelerstsmod", keyword.NAMES[0], keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }

    // 战斗结束时重置打出的咒语数为0
    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
        SpellStormPatch.spellUsed = 0;
    }
}
