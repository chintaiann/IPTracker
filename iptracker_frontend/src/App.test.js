import { TextField } from '@mui/material';
import { render, screen} from '@testing-library/react';
import App from './App';
import SingleQuery from './component/SingleQuery';
import userEvent from '@testing-library/user-event'


test('correct input of single queries', async () => { 
  const user = userEvent.setup(); 
  render(<SingleQuery/>)
  await user.click(screen.getByLabelText('singleQueryField'));
  // await user.keyboard("::ffff:100:a400");
  await user.keyboard("4.2.85.89");
  // user.click(screen.getByLabelText('singleQuerySubmit'));
  // user.click(screen.getByRole('button',{name:/checkbox/i}));
  await user.click(screen.getByText('Query IPv6/IPv4'));
  expect(await screen.findByText('4.2.85.89 is from United States of America')).toBeVisible();
  // expect(screen.getByText('4.2.85.89 is from United States of America')).toBeVisible();
});

test('incorrect format of single queries', async () => { 
  const user = userEvent.setup(); 
  render(<SingleQuery/>)
  await user.click(screen.getByLabelText('singleQueryField'));
  // await user.keyboard("::ffff:100:a400");
  await user.keyboard("4.2.85.");
  // user.click(screen.getByLabelText('singleQuerySubmit'));
  // user.click(screen.getByRole('button',{name:/checkbox/i}));
  await user.click(screen.getByText('Query IPv6/IPv4'));
  // expect(screen.getByText('4.2.85.89 is from United States of America')).toBeInTheDocument();
});