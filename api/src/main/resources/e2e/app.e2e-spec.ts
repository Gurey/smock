import { SmockPage } from './app.po';

describe('smock App', function() {
  let page: SmockPage;

  beforeEach(() => {
    page = new SmockPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('sm works!');
  });
});
