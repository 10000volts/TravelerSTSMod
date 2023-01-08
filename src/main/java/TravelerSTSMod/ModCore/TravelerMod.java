package TravelerSTSMod.ModCore;
import TravelerSTSMod.Cards.*;
import TravelerSTSMod.Characters.Traveler;
import TravelerSTSMod.Patches.SpellStormPatch;
import TravelerSTSMod.Potions.BottleSpell;
import TravelerSTSMod.Potions.Ink;
import TravelerSTSMod.Potions.Whisper;
import TravelerSTSMod.Relics.*;
import basemod.abstracts.CustomCard;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import basemod.BaseMod;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Prefs;
import com.megacrit.cardcrawl.localization.*;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

@SpireInitializer
public class TravelerMod implements EditCharactersSubscriber,EditCardsSubscriber,
        EditRelicsSubscriber, EditStringsSubscriber, EditKeywordsSubscriber, PostBattleSubscriber{
    public static final Logger logger = LogManager.getLogger(TravelerMod.class);

    // 人物选择界面按钮的图片
    private static final String MY_CHARACTER_BUTTON = "TravelerSTSModResources/img/charSelect/button.png";
    // 人物选择界面的立绘
    private static final String MY_CHARACTER_PORTRAIT = "TravelerSTSModResources/img/charSelect/travelerportrait.png";
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

        ArrayList<Prefs> prefs = CardCrawlGame.characterManager.getAllPrefs();

        receiveEditPotions();

//        Prefs p = prefs.get(4);
//        p.putInteger("ASCENSION_LEVEL", 20);
//        p.putInteger("LAST_ASCENSION_LEVEL", 20);
//        p.flush();
    }

    @Override
    public void receiveEditRelics() {
        BaseMod.addRelicToCustomPool(new BookAndQuill(), Traveler.Enums.TRAVELER_CARD);
        BaseMod.addRelicToCustomPool(new BlankMovement(), Traveler.Enums.TRAVELER_CARD);
        BaseMod.addRelicToCustomPool(new AbyssInkBottle(), Traveler.Enums.TRAVELER_CARD);
        BaseMod.addRelicToCustomPool(new MobiusBand(), Traveler.Enums.TRAVELER_CARD);
        BaseMod.addRelicToCustomPool(new Trinity(), Traveler.Enums.TRAVELER_CARD);
        BaseMod.addRelicToCustomPool(new SpellDictionary(), Traveler.Enums.TRAVELER_CARD);
        BaseMod.addRelicToCustomPool(new BloodSeeker(), Traveler.Enums.TRAVELER_CARD);
        BaseMod.addRelicToCustomPool(new Inferno(), Traveler.Enums.TRAVELER_CARD);
        BaseMod.addRelicToCustomPool(new SoulVessel(), Traveler.Enums.TRAVELER_CARD);
        BaseMod.addRelicToCustomPool(new Phonograph(), Traveler.Enums.TRAVELER_CARD);
        BaseMod.addRelicToCustomPool(new InkHourglass(), Traveler.Enums.TRAVELER_CARD);
        BaseMod.addRelicToCustomPool(new CrystalFromAbyss(), Traveler.Enums.TRAVELER_CARD);
        UnlockTracker.markRelicAsSeen(BookAndQuill.ID);
        UnlockTracker.markRelicAsSeen(BlankMovement.ID);
        UnlockTracker.markRelicAsSeen(AbyssInkBottle.ID);
        UnlockTracker.markRelicAsSeen(MobiusBand.ID);
        UnlockTracker.markRelicAsSeen(Trinity.ID);
        UnlockTracker.markRelicAsSeen(SpellDictionary.ID);
        UnlockTracker.markRelicAsSeen(BloodSeeker.ID);
        UnlockTracker.markRelicAsSeen(Inferno.ID);
        UnlockTracker.markRelicAsSeen(SoulVessel.ID);
        UnlockTracker.markRelicAsSeen(Phonograph.ID);
        UnlockTracker.markRelicAsSeen(InkHourglass.ID);
        UnlockTracker.markRelicAsSeen(CrystalFromAbyss.ID);
    }

    private void addPersonalities() {
        personalityPool.add(new Wrath(false));
        personalityPool.add(new Gluttony(false));
        personalityPool.add(new Lust(false));
        personalityPool.add(new Greed(false));
        personalityPool.add(new Sloth(false));
        personalityPool.add(new Envy(false));
        personalityPool.add(new PrideForm(false, false));
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
        spellPool.add(new SilentSpell(0));
    }

    @Override
    public void receiveEditCards() {
        addPersonalities();
        addSpells();
        ArrayList<CustomCard> cards = new ArrayList<>();
        cards.add(new Strike());
        cards.add(new InkDrop());
        cards.add(new Defend());
        cards.add(new EmptySpell(0));
        cards.add(new PainSpell(0));
        cards.add(new SuddenStrike());
        cards.add(new AmuletSpell(0));
        cards.add(new HeartLock());
        cards.add(new Melody());
        cards.add(new BarrierSpell(0));
        cards.add(new DistortingStrike());
        cards.add(new FromAbyss());
        cards.add(new Greed(false));
        cards.add(new MindShock());
        cards.add(new MindSnoop());
        cards.add(new RigmaroleSpell(0));
        cards.add(new Somniloquy());
        cards.add(new Swallow());
        cards.add(new Ghost());
        cards.add(new SpellStorm(0));
        cards.add(new Alchemy());
        cards.add(new BirthSpell(0));
        cards.add(new Envy(false));
        cards.add(new Hypnotize());
        cards.add(new InkSplash());
        cards.add(new ShuttleSpell(0));
        cards.add(new Sloth(false));
        cards.add(new Solidify());
        cards.add(new SurgeSpell(0));
        cards.add(new TurnPages());
        cards.add(new VajraSpell(0));
        cards.add(new VoidBelief());
        cards.add(new Wrath(false));
        cards.add(new ExplodeSpell(0));
        cards.add(new CriticalAttack(0, 0, CriticalAttack.COST));
        cards.add(new LiteraryTalent());
        cards.add(new ContractSpell(0));
        cards.add(new Transcend());
        cards.add(new Wisdom());
        cards.add(new Vacillate());
        cards.add(new Organize());
        cards.add(new Paranoid());
        cards.add(new SpellSearch(0));
        cards.add(new QuickSpell(0));
        cards.add(new Lust(false));
        cards.add(new ThinkHard());
        cards.add(new Recount());
        cards.add(new Degenerate());
        cards.add(new Gluttony(false));
        cards.add(new SpellAmplify(0));
        cards.add(new VoiceOfDark());
        cards.add(new EchoSpell(0));
        cards.add(new Inspire());
        cards.add(new Palindrome());
        cards.add(new TimePassed());
        cards.add(new TemblorSpell(0));
        cards.add(new Tribute());
        cards.add(new Recall());
        cards.add(new Chant());
        cards.add(new SpellIntensify(0));
        cards.add(new GazeOfAbyss());
        cards.add(new Inking());
        cards.add(new Illusion());
        cards.add(new Squeeze());
        cards.add(new AbsorbSpell(0));
        cards.add(new BadOmen());
        cards.add(new SpellKit(0));
        cards.add(new PrideForm(false, false));
        cards.add(new QuickCasting());
        cards.add(new MindSplit());
        cards.add(new SilentSpell(0));
        cards.add(new AnotherPath());
        cards.add(new Notes());
        cards.add(new Caustic());
        cards.add(new MemoryLoss());
        for (CustomCard c : cards) {
            System.out.println(c.name);
            UnlockTracker.unlockCard(c.cardID);
            BaseMod.addCard(c);
        }
    }

    public void receiveEditPotions() {
        BaseMod.addPotion(Ink.class, null, null, null, Ink.POTION_ID, Traveler.Enums.TRAVELER);
        BaseMod.addPotion(BottleSpell.class, null, null, null, BottleSpell.POTION_ID, Traveler.Enums.TRAVELER);
        BaseMod.addPotion(Whisper.class, Color.SCARLET.cpy(), Color.BLACK.cpy(), null, Whisper.POTION_ID, Traveler.Enums.TRAVELER);
    }

    @Override
    public void receiveEditStrings() {
        String lang = "ZHS";
        switch (Settings.language) {
            case ENG:
                lang = "ENG";
        }
        String llPath = "TravelerSTSModResources/localization/";
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
        String potionStrings = Gdx.files.internal(llPath + lang + "/potion.json").readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(PotionStrings.class, potionStrings);

        BaseMod.addColor(Traveler.Enums.TRAVELER_CARD, MY_COLOR, MY_COLOR, MY_COLOR, MY_COLOR, MY_COLOR, MY_COLOR, MY_COLOR,
                BG_ATTACK_512,BG_SKILL_512,BG_POWER_512,ENEYGY_ORB,BG_ATTACK_1024,BG_SKILL_1024,BG_POWER_1024,BIG_ORB,
                SMALL_ORB);
    }

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String llPath = "TravelerSTSModResources/localization/";
        String lang = "ZHS";
        switch (Settings.language) {
            case ENG:
                lang = "ENG";
        }

        String json = Gdx.files.internal(llPath + lang + "/keyword.json")
                .readString(String.valueOf(StandardCharsets.UTF_8));
        Keyword[] keywords = gson.fromJson(json, Keyword[].class);
        if (keywords != null) {
            for (Keyword keyword : keywords) {
                String name = keyword.NAMES[0];
                if (lang.equals("ENG")) {
                    for (int i = 0; i < keyword.NAMES.length; ++i) {
                        keyword.NAMES[i] = keyword.NAMES[i].toLowerCase();
                    }
                }
                BaseMod.addKeyword("travelerstsmod", name, keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }

    // 战斗结束时重置打出的咒语数为0
    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
        SpellStormPatch.spellUsed = 0;
    }
}
