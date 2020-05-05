<template>
  <div id="home-page">
    <template v-if="!isLoggedIn">
      <h1>Welcome to LangReader!</h1>
      <hr>
      <p>This is a website for studying languages through reading. The main idea is that
        you add texts you actually want to read and understand, and by selecting which words you
        know, we track your progress in your language learning.</p>
      <p>You can select words you don't know, and the app will keep track of them for you, so you
        can go back to them later.</p>
      <p>You can create your own texts either by copying it from the original source to the
        prepared text area, or by copying and pasting the URL address of an article. A special
        algorithm will try to save the main text from the provided URL. This might not be 100%
        successful, but based on my testing, it seems fairly accurate. If the text is too long,
        it will be split into manageable chunks, in order for the webpage to stay responsive.</p>
      <p>I wish you a pleasant learning experience with this website.</p>
      <p>.</p>
      <p>Planned features:</p>
      <ul>
        <li>instant translation of a word marked as studied</li>
        <li>editing already submitted texts</li>
        <li>section for going through studied words (+ their translation)</li>
        <li>possibly export of these studied words into a structured file (e.g. for Anki)</li>
        <li>touch controls for reading even on a smartphone / tablet</li>
        <li>hypothetically: upload an ebook file and automatically split it into chapters and
          add those to the library
        </li>
      </ul>
    </template>
    <template v-else>
      <h1>Tutorial</h1>
      <hr>
      <p>This web application currently consists of several sections.</p>

      <h2>Languages</h2>
      <p>The language section allows you to manage languages you're interested in, and switch
        between them as you go. The currently selected language is then visible in the navigation
        menu.</p>
      <p>You can also remove the languages. But be careful. Removing the language results in
        removing
        all the texts and words added in that language (that is, added while the language was
        selected).</p>
      <p>After choosing your first language, a new section will appear in the menu - your
        library.</p>

      <h2>Library</h2>
      <h3>Adding texts</h3>
      <p>The library contains all your texts in the currently selected language.
        It allows searching through them according to their title.</p>
      <p>There is a plus button on the right of the search bar, that allows you to add more texts,
        either by pasting the text directly into the text area, or by providing a URL address,
        from which the app will attempt to extract the main text into a new library item.</p>
      <p>If the text is too long, it will be split into several parts, which will be labeled
        accordingly. This keeps the loading time of each text acceptable.</p>
      <p>Each text is shown by its title, from the newest all the way to the oldest one. There are
        buttons available on the right side, which allow you to edit the text
        (<i>to be implemented</i>), or remove it.</p>
      <h3>Reading</h3>
      <p>After adding your first text, you can start learning by clicking on it. The text will
        open, and all the individual words will be highlighted according to their current status.
        Every word can be in one of the following states (represented by these colors):</p>
      <ul>
        <li><span>unknown</span> - a word not yet encountered</li>
        <li><span class="KNOWN">known</span> - a word marked as already known</li>
        <li><span class="STUDIED">studied</span> - a word marked for further study</li>
        <li><span class="IGNORED">ignored</span> - a word marked as ignored
          (for example, words that are not part of the language, like names, brands, locations etc.)
        </li>
      </ul>
      <p>A known word and an ignored word will look exactly the same. That is intentional. After
        marking them as such, these shouldn't distract you anymore from the more significant
        words.</p>
      <h3>Controls</h3>
      <p>After the loading is finished, you can select the first word by a mouse click. Selecting
        surrounding words after that can be accomplished with <span class="key arrow-key">←</span>
        and
        <span class="key arrow-key">→</span> arrow keys.</p>
      <p>You can mark words by keys:</p>
      <ul>
        <li><span class="key a-key">a</span> - marks a word as <b>known</b></li>
        <li><span class="key s-key">s</span> - marks a word as <b>studied</b></li>
        <li><span class="key d-key">d</span> - marks a word as <b>ignored</b></li>
        <li><span class="key r-key">r</span> - resets the state of a word (to <b>unknown</b>)</li>
      </ul>
      <p>After each key press, every occurrence of the marked word in the text will change its
        state,
        and the focus will automatically move onto the next unknown word.</p>
      <p>Note that your learning progress isn't lost even if you remove the texts after you finish
        reading them. That way, you can keep in your library only those texts, that are still
        relevant to
        your learning.</p>
      <h2>Account Page</h2>
      <p>Here you will find statistics about marked words in your studied languages. This page
        will be expanded in the future to provide even more statistics and account information.</p>
    </template>
  </div>
</template>

<script>
  export default {
    computed: {
      isLoggedIn() {
        return this.$store.getters["auth/isLoggedIn"];
      }
    },
    methods: {}
  }
</script>

<style scoped>
  h3 {
    font-size: 1.3em;
    color: var(--active-el-color-darkest);
  }

  ul {
    margin: -5px 30px;
  }

  li {
    padding: 5px;
    list-style: disc;
  }

  span {
    padding: 1px 0;
    background-color: #cfe6ff;
    border-radius: 3px;
    text-decoration: none;
    color: var(--default-text-color);
  }

  .KNOWN {
    background-color: white;
  }

  .STUDIED {
    background-color: yellow;
  }

  .IGNORED {
    background-color: white;
  }

  .key {
    border: 1px solid var(--default-text-color);
    background-color: white;
    font-size: 1.1em;
    line-height: 1.3;
  }

  .arrow-key {
    line-height: 1.5;
    padding: 0 3px;
    margin: 0 5px;
  }

  .a-key {
    padding: 0 7px 0 8px;
  }

  .s-key {
    padding: 0 8px;
  }

  .d-key {
    padding: 0 7px 0 8px;
  }

  .r-key {
    padding: 0 9px 0 10px;
  }
</style>
